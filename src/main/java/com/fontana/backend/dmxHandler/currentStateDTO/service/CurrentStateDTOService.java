package com.fontana.backend.dmxHandler.currentStateDTO.service;

import com.fontana.backend.devices.dto.DeviceDTO;
import com.fontana.backend.dmxHandler.currentStateDTO.CurrentStateDTO;
import org.springframework.stereotype.Service;

@Service
public class CurrentStateDTOService {
    public void appendToArray(DeviceDTO deviceDTO, CurrentStateDTO currentStateDTO) {
        currentStateDTO.getCurrentStateArray().add(deviceDTO);
    }
}
