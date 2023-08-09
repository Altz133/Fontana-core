package com.fontana.backend.devices.led.mapper;

import com.fontana.backend.devices.entity.Device;
import com.fontana.backend.devices.led.dto.LedDTO;
import com.fontana.backend.devices.led.entity.Led;
import com.fontana.backend.devices.repository.DeviceRepository;
import org.springframework.stereotype.Service;

@Service
public class LedMapper {
    DeviceRepository deviceRepository;

    public LedMapper() {
    }

    //FIXME - Mapper potrzebny
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
                addresses[0], ledDTO.getColorR(),
                addresses[1], ledDTO.getColorG(),
                addresses[2], ledDTO.getColorB(),
                addresses[3], ledDTO.getColorW(),
                addresses[4], ledDTO.getPower(),
                addresses[5], ledDTO.getStroboscopeFrequency());
    }
}
