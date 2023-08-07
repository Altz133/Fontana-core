package com.fontana.backend.devices.jet.dto;

import com.fontana.backend.devices.dto.DeviceDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JetDTO extends DeviceDTO {
    private Boolean value;
}
