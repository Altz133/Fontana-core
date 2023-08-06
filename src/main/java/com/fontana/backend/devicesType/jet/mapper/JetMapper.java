package com.fontana.backend.devicesType.jet.mapper;

import com.fontana.backend.devices.entity.Device;
import com.fontana.backend.devices.repository.DeviceRepository;
import com.fontana.backend.devicesType.jet.dto.JetDTO;
import com.fontana.backend.devicesType.jet.entity.Jet;
import org.springframework.stereotype.Service;

@Service
public class JetMapper {
    DeviceRepository deviceRepository;
    public JetDTO JetToDTO(Jet jet){
        return new JetDTO();
    }

    public Jet DTOToJet(JetDTO jetDTO){
        Device device = deviceRepository.findByname(jetDTO.getName());
        return new Jet(device.getId(), jetDTO.getValue());
    }
}
