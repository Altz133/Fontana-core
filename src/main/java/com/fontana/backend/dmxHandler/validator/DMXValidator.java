package com.fontana.backend.dmxHandler.validator;

import com.fontana.backend.devices.entity.Device;
import com.fontana.backend.devices.repository.DeviceRepository;
import com.fontana.backend.frame.entity.Frame;
import com.fontana.backend.sensorsHandler.entity.Sensors;
import com.fontana.backend.sensorsHandler.service.SensorsHandlerService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DMXValidator {
    @Autowired
    private DeviceRepository deviceRepository;
    private final SensorsHandlerService sensorsHandlerService;
    byte[] dmxDataZero = new byte[515];

    @PostConstruct
    public void init() {
        try {
            initialSetup();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public byte[] validateDmxData(byte[] dmxData, Frame frame) throws IOException {
        byte[] data = Arrays.copyOf(dmxData,dmxData.length);
        data[frame.getId()] = frame.getValue();
        if (validateWaterLevel()){
            return validateArray(data);
        }
        return dmxDataZero;
    }

    public byte[] validateArray(byte[] dmxData) {
        String type = "pump";
        List<Device> pumps = deviceRepository.findByType(type);
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
            if (closedValveCounter == singlePumpAddresses.length && pumpPower != 0) {
                throw new IllegalArgumentException(type +" "+ pumpId + " is on, but all valves are closed");
            }
            if(closedValveCounter >0 && pumpPower > 255 * (1 - (0.1 * closedValveCounter))){
                dmxData[pumpId] = (byte) (255 * (1 - (0.1 * closedValveCounter)));
            }

        }
        return dmxData;
    }

    public boolean validateWaterLevel() throws IOException {
        Sensors sensors = sensorsHandlerService.getSensors();
        if(!sensors.getWaterBottom() || !sensors.getWaterTop()){
            return true;
        }
        else{
            throw new IOException("Water level is too high or too low");
        }
    }

    void initialSetup() throws IOException {
        dmxDataZero = new byte[515];
        for (int j = 0; j < 512; j++) {
            dmxDataZero[j] = 0;
        }
        dmxDataZero[dmxDataZero.length - 3] = 33;
        dmxDataZero[dmxDataZero.length - 2] = 22;
        dmxDataZero[dmxDataZero.length - 1] = 11;
    }
}