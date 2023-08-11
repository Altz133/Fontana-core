package com.fontana.backend.dmxHandler.validator;

import com.fontana.backend.devices.entity.Device;
import com.fontana.backend.devices.repository.DeviceRepository;
import com.fontana.backend.frame.entity.Frame;
import com.fontana.backend.sensorsHandler.entity.Sensors;
import com.fontana.backend.sensorsHandler.service.SensorsHandlerService;
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

    public boolean validateDmxData(byte[] dmxData, Frame frame) throws IOException {
        //FIXME na razie nie ma dostępu do serwera
        //return validateArray(dmxData) && validateWaterLevel();
        byte[] data = Arrays.copyOf(dmxData,dmxData.length);
        data[frame.getId()] = frame.getValue();
        return validateArray(data);
    }

    public boolean validateArray(byte[] dmxData) {
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

        }
        return true;
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
}