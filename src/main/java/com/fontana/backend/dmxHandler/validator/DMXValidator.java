package com.fontana.backend.dmxHandler.validator;

import com.fontana.backend.devices.entity.Device;
import com.fontana.backend.devices.repository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DMXValidator {
    DeviceRepository deviceRepository;

    public boolean validate(byte[] dmxData) {
        List<Device> pumps = deviceRepository.findByType("Pump");
        for (Device pump : pumps) {

            int[] singlePumpAddresses = pump.getAddresses();
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
                return false;
            }

        }
        return true;
    }
}