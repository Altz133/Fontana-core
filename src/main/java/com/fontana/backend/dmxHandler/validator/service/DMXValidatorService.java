package com.fontana.backend.dmxHandler.validator.service;

import com.fontana.backend.devices.entity.Device;
import com.fontana.backend.devices.entity.DeviceType;
import com.fontana.backend.devices.repository.DeviceRepository;
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
    public static boolean enableApiValidation = false;
    private static float pumpPowerMultiplier = 0.1f;
    private static final int byteMaxValue = 255;
    @Autowired
    private final SensorsHandlerService sensorsHandlerService;
    @Autowired
    private final DeviceRepository deviceRepository;
    private Sensors sensors;
    private List<Device> pumps;
    private List<Device> leds;
    private List<Device> lights;

    public static void changePumpMultiplier(float multiplier) {
        pumpPowerMultiplier = multiplier;
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
        if (enableApiValidation){
            return validateArray(data,sensors);
        }
        return validateArray(data);
    }

    //validation without sensors
    public byte[] validateArray(byte[] dmxData) {
        for (Device pump : pumps) {
            int[] singlePumpAddresses = pump.getAddress();
            int pumpId = pump.getId();
            int pumpPower =dmxData[pumpId] & 0xFF;
            int closedValveCounter = 0;

            for (int jetId : singlePumpAddresses) {
                byte jetPower = dmxData[jetId];
                if (jetPower == 0) {
                    closedValveCounter++;
                }
            }
            if (closedValveCounter > 0 && closedValveCounter != singlePumpAddresses.length && pumpPower > (byteMaxValue * (1 - (pumpPowerMultiplier * closedValveCounter))) && pumpPower != 0) {
                dmxData[pumpId] = (byte) ( (byteMaxValue * (1 - (pumpPowerMultiplier * closedValveCounter))));
            }
            if (closedValveCounter == singlePumpAddresses.length && pumpPower != 0) {
                dmxData[pumpId] = 0;
                throw new DMXValidatorException("Pump " + pumpId + DMXValidatorMessages.CLOSED_VALVES.getMessage());
            }
        }
        return dmxData;
    }

    //validation with sensors
    public byte[] validateArray(byte[] dmxData, Sensors sensors) {
        byte[] validatedArray = validateArray(dmxData);
        return validateArrayCyclic(validatedArray);
    }

    public byte[] validateArrayCyclic(byte[] dmxData){
        //turning off the pumps if the water level is too low
        if (sensors.getWaterBottom()) {
            for(Device pump : pumps){
                int pumpId = pump.getId();
                dmxData[pumpId] = 0;
            }
        }
        //turning off the lights if the water level is too high
        if(sensors.getWaterTop()){
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