package com.fontana.backend.dmxHandler.validator;

import com.fontana.backend.devices.entity.Device;
import com.fontana.backend.devices.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DMXValidator {
    private final DeviceRepository deviceRepository;

    public boolean validate(byte[] dmxData) {
        List<Device> pumps = deviceRepository.findByType("Pump");
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
                return false;
            }

        }
        return true;
    }
}