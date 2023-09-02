package com.fontana.backend.dmxHandler.validator.service;

import com.fontana.backend.devices.entity.Device;
import com.fontana.backend.devices.entity.DeviceType;
import com.fontana.backend.devices.repository.DeviceRepository;
import com.fontana.backend.dmxHandler.DMXService;
import com.fontana.backend.dmxHandler.validator.messages.DMXValidatorMessages;
import com.fontana.backend.exception.customExceptions.DMXValidatorException;
import com.fontana.backend.frame.entity.Frame;
import com.fontana.backend.sensorsHandler.entity.Sensors;
import com.fontana.backend.sensorsHandler.service.SensorsHandlerService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class DMXValidatorService {
    private static final int byteMaxValue = 255;
    public static boolean enableApiValidation = false;
    private static float pumpPowerMultiplier = 0.1f;
    private static boolean waterLevelTopStatus = false;
    private static boolean waterLevelBottomStatus = true;
    private static Sensors sensors;
    private static List<Device> pumps;
    private static List<Device> leds;
    private static List<Device> lights;
    @Autowired
    private final SensorsHandlerService sensorsHandlerService;
    @Autowired
    private final DeviceRepository deviceRepository;

    public static void changePumpMultiplier(float multiplier) {
        if (validatePumpMultiplier(multiplier)) {
            pumpPowerMultiplier = multiplier;
        } else {
            throw new DMXValidatorException(DMXValidatorMessages.INVALID_PUMP_MULTIPLIER.getMessage());
        }
    }

    public static void setStatusesToEnabled() {
        waterLevelTopStatus = false;
        waterLevelBottomStatus = true;
    }

    public static boolean getValidationStatus() {
        return enableApiValidation;
    }

    public static float getPumpMultiplier() {
        return pumpPowerMultiplier;
    }

    public static Boolean validatePumpMultiplier(float multiplier) {
        return multiplier >= 0 && multiplier <= 1;
    }

    public static void runCyclicValidation() {
        validateArrayCyclic(DMXService.getDMXData());
    }

    public static byte[] validateLightsAndLeds(byte[] dmxData) {
        if (sensors.getWaterTop()) {
            for (Device led : leds) {
                int[] singleLedAddresses = led.getAddress();
                for (int ledId : singleLedAddresses) {
                    dmxData[ledId] = 0;
                }
            }
            for (Device light : lights) {
                int[] singleLightAddresses = light.getAddress();
                for (int lightId : singleLightAddresses) {
                    dmxData[lightId] = 0;
                }
            }
            waterLevelBottomStatus = true;
            return dmxData;
        }
        waterLevelBottomStatus = false;
        return dmxData;
    }

    public static byte[] validatePumpsAPI(byte[] dmxData) {
        //turning off the pumps if the water level is too low
        if (!sensors.getWaterBottom()) {
            for (Device pump : pumps) {
                int pumpId = pump.getId();
                dmxData[pumpId] = 0;
            }
            waterLevelTopStatus = false;
            return dmxData;
        }
        waterLevelTopStatus = true;
        return dmxData;
    }

    public static byte[] validateArrayCyclic(byte[] dmxData) {
        //turning off the pumps if the water level is too low
        byte[] validatedPumpsAndJets = validatePumpsAPI(dmxData);
        //turning off the lights if the water level is too high
        return validateLightsAndLeds(validatedPumpsAndJets);
    }

    @PostConstruct
    public void init() throws IOException {
        sensors = sensorsHandlerService.getSensors();
        pumps = deviceRepository.findByType(DeviceType.PUMP);
        leds = deviceRepository.findByType(DeviceType.LED);
        lights = deviceRepository.findByType(DeviceType.LIGHT);
    }

    public void updateDMXAddresses() {
        pumps = deviceRepository.findByType(DeviceType.PUMP);
        leds = deviceRepository.findByType(DeviceType.LED);
        lights = deviceRepository.findByType(DeviceType.LIGHT);
    }

    public byte[] validateDmxData(byte[] dmxData, Frame frame) throws IOException {

        byte[] data = Arrays.copyOf(dmxData, dmxData.length);
        data[frame.getId()] = frame.getValue();
        Device device = deviceRepository.findById(frame.getId()).orElse(null);

        if (device == null) {
            device = deviceRepository.findContainedAddress(frame.getId());
            if (device.getId() != 0 && waterLevelTopStatus) {
                return data;
            }
            return dmxData;
        }

        DeviceType type = device.getType();

        switch (type) {
            case PUMP -> {
                if (waterLevelBottomStatus) {
                    return validateArray(data);
                }
            }
            case JET -> {
                return validateArray(data);
            }
            case LED, LIGHT -> {
                if (waterLevelTopStatus) {
                    return data;
                }
            }
        }
        return dmxData;
    }

    //validation without sensors
    public byte[] validateArray(byte[] dmxData) {
        for (Device pump : pumps) {
            int[] singlePumpAddresses = pump.getAddress();
            int pumpId = pump.getId();
            int pumpPower = dmxData[pumpId] & 0xFF;
            int closedValveCounter = 0;

            for (int jetId : singlePumpAddresses) {
                byte jetPower = dmxData[jetId];
                if (jetPower == 0) {
                    closedValveCounter++;
                }
            }
            if (closedValveCounter > 0 && closedValveCounter != singlePumpAddresses.length && pumpPower > (byteMaxValue * (1 - (pumpPowerMultiplier * closedValveCounter))) && pumpPower != 0) {
                dmxData[pumpId] = (byte) ((byteMaxValue * (1 - (pumpPowerMultiplier * closedValveCounter))));
            }
            if (closedValveCounter == singlePumpAddresses.length && pumpPower != 0) {
                dmxData[pumpId] = 0;
            }
        }
        return dmxData;
    }

    //every 30 seconds update the sensors data
    @Scheduled(fixedRate = 30000L)
    public void getSensorData() throws IOException {
        if (enableApiValidation) {
            sensors = sensorsHandlerService.getSensors();
        }
    }
}