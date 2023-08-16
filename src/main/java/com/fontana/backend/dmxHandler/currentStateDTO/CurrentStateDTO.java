package com.fontana.backend.dmxHandler.currentStateDTO;

import com.fontana.backend.devices.dto.DeviceDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CurrentStateDTO {
    public List<DeviceDTO> currentStateArray = new ArrayList<>();
}
