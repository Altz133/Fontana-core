package com.fontana.backend.dmxHandler.validator.service;

import com.fontana.backend.devices.entity.Device;
import com.fontana.backend.devices.entity.DeviceType;
import com.fontana.backend.devices.repository.DeviceRepository;
import com.fontana.backend.dmxHandler.validator.messages.DMXValidatorMessages;
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

    public void updateDMXAddresses(){
        pumps = deviceRepository.findByType(DeviceType.PUMP);
        leds = deviceRepository.findByType(DeviceType.LED);
        lights = deviceRepository.findByType(DeviceType.LIGHT);
    }

    public byte[] validateDmxData(byte[] dmxData, Frame frame) throws IOException {
        byte[] data = Arrays.copyOf(dmxData, dmxData.length);
        data[frame.getId()] = frame.getValue();
        if (enableApiValidation) {
            return validateArray(data, sensors);
        }
        return validateArray(data);
    }

    //walidacja bez sensorow
    public byte[] validateArray(byte[] dmxData) {
        for (Device pump : pumps) {

            int[] singlePumpAddresses = pump.getAddress();
            int pumpId = pump.getId();
            byte pumpPower = dmxData[pumpId];
            int closedValveCounter = 0;

            for (int jetId : singlePumpAddresses) {
                byte jetPower = dmxData[jetId];
                if (jetPower == 0) {
                    closedValveCounter++;
                }
            }
            if (closedValveCounter > 0 && pumpPower > (byte) (255 * (1 - (pumpPowerMultiplier * closedValveCounter))) && pumpPower != 0) {
                dmxData[pumpId] = (byte) (255 * (1 - (pumpPowerMultiplier * closedValveCounter)));
                throw new RuntimeException("Pump " + pumpId + DMXValidatorMessages.RELATIVE_POWER);
            }
            if (closedValveCounter == singlePumpAddresses.length && pumpPower != 0) {
                dmxData[pumpId] = 0;
                throw new RuntimeException("Pump " + pumpId + DMXValidatorMessages.CLOSED_VALVES);
            }
        }
        return dmxData;
    }

    public byte[] validateArray(byte[] dmxData, Sensors sensors) {
        for (Device pump : pumps) {

            int[] singlePumpAddresses = pump.getAddress();
            int pumpId = pump.getId();
            byte pumpPower = dmxData[pumpId];
            int closedValveCounter = 0;

            for (int jetId : singlePumpAddresses) {
                byte jetPower = dmxData[jetId];
                if (jetPower == 0) {
                    closedValveCounter++;
                }
            }
            if (closedValveCounter > 0 && pumpPower > (byte) (255 * (1 - (pumpPowerMultiplier * closedValveCounter))) && pumpPower != 0) {
                dmxData[pumpId] = (byte) (255 * (1 - (pumpPowerMultiplier * closedValveCounter)));
                throw new RuntimeException("Pump " + pumpId + DMXValidatorMessages.RELATIVE_POWER);
            }
            //wyłączenie pompy jeśli wszystkie zawory są zamknięte
            if (closedValveCounter == singlePumpAddresses.length && pumpPower != 0) {
                dmxData[pumpId] = 0;
                throw new RuntimeException("Pump " + pumpId + DMXValidatorMessages.CLOSED_VALVES);
            }
            //wyłączenie pomp jeśli poziom wody jest za niski
            if (sensors.getWaterBottom()) {
                dmxData[pumpId] = 0;
                throw new RuntimeException(String.valueOf(DMXValidatorMessages.LOW_WATER.getMessage()));
            }
        }
        //wyłączenie świateł i ledów jeśli poziom wody jest za wysoki
        if (sensors.getWaterTop()) {
            //konkatenacja zmniejszy wydajnosc
            //wylaczanie ledow
            for (Device led : leds) {
                int[] singleLedAddresses = led.getAddress();
                for (int ledId : singleLedAddresses) {
                    dmxData[ledId] = 0;
                }
            }
            //wylaczanie swiatel
            for (Device light : lights) {
                int[] singleLightAddresses = light.getAddress();
                for (int lightId : singleLightAddresses) {
                    dmxData[lightId] = 0;
                }
            }
            throw new RuntimeException(DMXValidatorMessages.OVERFLOWING.getMessage());
        }
        return dmxData;
    }

    //co 30 sekund pobiera dane z sensorow
    @Scheduled(fixedRate = 30000L)
    public void getSensorData() throws IOException {
        if(enableApiValidation){
        sensors = sensorsHandlerService.getSensors();
    }}
}