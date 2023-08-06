package com.fontana.backend.devicesType.light.mapper;

import com.fontana.backend.devices.entity.Device;
import com.fontana.backend.devices.repository.DeviceRepository;
import com.fontana.backend.devicesType.light.dto.LightDTO;
import com.fontana.backend.devicesType.light.entity.Light;
import org.springframework.stereotype.Service;

@Service
public class LightMapper {
    DeviceRepository deviceRepository;
    //FIXME trzeba sie dopytac czy kazdy kanaal ma wlasne id
    public Light DTOToLight(LightDTO lightDTO){
        Device device = deviceRepository.findByname(lightDTO.getName());
        return new Light();
    }

    public LightDTO LightToDTO(Light light){
        return new LightDTO();
    }

}
