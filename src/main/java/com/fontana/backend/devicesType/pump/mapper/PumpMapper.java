package com.fontana.backend.devicesType.pump.mapper;

import com.fontana.backend.devices.entity.Device;
import com.fontana.backend.devices.repository.DeviceRepository;
import com.fontana.backend.devicesType.pump.dto.PumpDTO;
import com.fontana.backend.devicesType.pump.entity.Pump;
import org.springframework.stereotype.Service;

@Service
public class PumpMapper {
    DeviceRepository deviceRepository;
    public Pump DTOToPump(PumpDTO pumpDTO){
        Device device = deviceRepository.findByName(pumpDTO.getName());
        return new Pump(device.getId(), pumpDTO.getValue());
    }

    public PumpDTO PumpToDTO(Pump pump){
        return new PumpDTO();
    }

}

