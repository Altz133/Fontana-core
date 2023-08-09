package com.fontana.backend.devices.pump.mapper;

import com.fontana.backend.devices.entity.Device;
import com.fontana.backend.devices.pump.dto.PumpDTO;
import com.fontana.backend.devices.pump.entity.Pump;
import com.fontana.backend.devices.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PumpMapper {
    @Autowired
    private DeviceRepository deviceRepository;

    public Pump DTOToPump(PumpDTO pumpDTO) {
        Device device = deviceRepository.findByName(pumpDTO.getName());
        return new Pump(device.getId(), pumpDTO.getValue());
    }

    public PumpDTO PumpToDTO(Pump pump) {
        return new PumpDTO();
    }

}

