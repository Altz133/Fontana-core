package com.fontana.backend.devicesType.light.mapper;

import com.fontana.backend.devices.entity.Device;
import com.fontana.backend.devices.repository.DeviceRepository;
import com.fontana.backend.devicesType.light.dto.LightDTO;
import com.fontana.backend.devicesType.light.entity.Light;
import org.springframework.stereotype.Service;

@Service
public class LightMapper {
    DeviceRepository deviceRepository;
    public Light DTOToLight(LightDTO lightDTO){
        Device device = deviceRepository.findByName(lightDTO.getName());
        byte[] addresses = device.getAddresses();
        return new Light(
                addresses[0],lightDTO.getColorR(),
                addresses[1],lightDTO.getColorG(),
                addresses[2],lightDTO.getColorB());
    }

    public LightDTO LightToDTO(Light light){
        return new LightDTO();
    }

}
