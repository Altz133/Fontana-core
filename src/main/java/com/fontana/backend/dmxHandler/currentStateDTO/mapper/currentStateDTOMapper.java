package com.fontana.backend.dmxHandler.currentStateDTO.mapper;

import com.fontana.backend.devices.entity.Device;
import com.fontana.backend.devices.repository.DeviceRepository;
import com.fontana.backend.dmxHandler.currentStateDTO.currentStateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class currentStateDTOMapper {
    @Autowired
    DeviceRepository deviceRepository;

    public currentStateDTO DMXtoCurrentStateDTO(byte[] DMXDataArray) {
        currentStateDTO currentStateDTO = new currentStateDTO();
        for (int i = 0; i < DMXDataArray.length -3; i++) {
            Optional<Device> device = deviceRepository.findById(i);
            if (device.isPresent()) {

            }
        }

        return currentStateDTO;
    }
}


