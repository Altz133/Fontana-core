package com.fontana.backend.devices.led.mapper;

import com.fontana.backend.devices.entity.Device;
import com.fontana.backend.devices.led.dto.LedDTO;
import com.fontana.backend.devices.led.entity.Led;
import com.fontana.backend.devices.repository.DeviceRepository;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LedMapper {
    private Validator validator;
    @Autowired
    private DeviceRepository deviceRepository;

    public LedDTO LedToDTO(Led led) {
        return new LedDTO();
    }

    public Led DTOToLed(LedDTO ledDTO) {
        Device device = deviceRepository.findByName(ledDTO.getName());
        int[] addresses = device.getAddress();
                /*
                0 - Red
                1 - Green
                2 - Blue
                3 - White
                4 - Dimm.../.
                5 - Strobe
                 */
        return new Led(
                addresses[0], (byte) ledDTO.getColorR(),
                addresses[1], (byte) ledDTO.getColorG(),
                addresses[2], (byte) ledDTO.getColorB(),
                addresses[3], (byte) ledDTO.getColorW(),
                addresses[4], (byte) ledDTO.getPower(),
                addresses[5], (byte) ledDTO.getStroboscopeFrequency());
    }
}
