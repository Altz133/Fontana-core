package com.fontana.backend.devices.jet.mapper;

import com.fontana.backend.devices.entity.Device;
import com.fontana.backend.devices.jet.dto.JetDTO;
import com.fontana.backend.devices.jet.entity.Jet;
import com.fontana.backend.devices.repository.DeviceRepository;
import org.springframework.stereotype.Service;

@Service
public class JetMapper {
    DeviceRepository deviceRepository;

    public JetDTO JetToDTO(Jet jet) {
        return new JetDTO();
    }

    public Jet DTOToJet(JetDTO jetDTO) {
        Device device = deviceRepository.findByName(jetDTO.getName());
        return new Jet(device.getId(), isEnabled(jetDTO.getValue()));
    }

    public byte isEnabled(boolean enabler) {
        if (enabler) {
            return (byte) 200;
        }
        return (byte) 0;
    }
}
