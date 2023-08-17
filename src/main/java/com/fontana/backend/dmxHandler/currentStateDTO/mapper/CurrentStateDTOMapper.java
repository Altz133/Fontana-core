package com.fontana.backend.dmxHandler.currentStateDTO.mapper;

import com.fontana.backend.devices.dto.DeviceDTO;
import com.fontana.backend.devices.entity.Device;
import com.fontana.backend.dmxHandler.currentStateDTO.factory.DeviceDTOFactory;
import com.fontana.backend.dmxHandler.currentStateDTO.service.CurrentStateDTOService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CurrentStateDTOMapper {

    private final DeviceDTOFactory deviceDTOFactory;
    private final CurrentStateDTOService currentStateDTOService;

    public List<DeviceDTO> DMXtoCurrentStateDTO(byte[] DMXDataArray) {

        List<DeviceDTO> currentStateDTO = new ArrayList<>();
        List<Device> devices = currentStateDTOService.getDevices();

        for (Device device : devices) {
            currentStateDTO.add(deviceDTOFactory.createDeviceDTO(device, DMXDataArray, device.getAddress()));
        }

        return currentStateDTO;
    }
}


