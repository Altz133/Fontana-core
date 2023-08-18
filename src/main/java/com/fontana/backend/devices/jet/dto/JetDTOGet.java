package com.fontana.backend.devices.jet.dto;

import com.fontana.backend.devices.dto.DeviceDTO;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class JetDTOGet extends DeviceDTO {
    private Boolean value;

    public JetDTOGet(String name, Boolean value) {
        super(name);
        this.value = value;
    }
}
