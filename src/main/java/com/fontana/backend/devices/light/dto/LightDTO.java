package com.fontana.backend.devices.light.dto;

import com.fontana.backend.devices.dto.DeviceDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LightDTO extends DeviceDTO {

    private byte colorR;
    private byte colorG;
    private byte colorB;


}
