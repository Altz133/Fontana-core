package com.fontana.backend.devices.jet.mapper;

import com.fontana.backend.devices.entity.Device;
import com.fontana.backend.devices.jet.dto.JetDTO;
import com.fontana.backend.devices.jet.entity.Jet;
import com.fontana.backend.devices.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JetMapper {
    private final DeviceRepository deviceRepository;

    public JetDTO JetToDTO(Jet jet) {
        return new JetDTO();
    }

    public Jet DTOToJet(JetDTO jetDTO) {
        System.out.println(deviceRepository.findByName(jetDTO.getName()));
        Device device = deviceRepository.findByName(jetDTO.getName());
        return new Jet(device.getId(), checkIfEnabled(jetDTO.getValue()));
    }

    public byte checkIfEnabled(boolean isEnabled) {
        if (isEnabled) {
            return (byte) 255;
        }
        return (byte) 0;
    }
}
