package com.fontana.backend.dmxHandler.currentStateDTO.service;

import com.fontana.backend.devices.entity.Device;
import com.fontana.backend.devices.repository.DeviceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@Service
public class CurrentStateDTOService {
    private final DeviceRepository deviceRepository;
    public Optional<Device> getDevice(int i) {
        return deviceRepository.findById(i);
    }

    public List<Device> getDevices() {
        return deviceRepository.findAll();
    }
}
