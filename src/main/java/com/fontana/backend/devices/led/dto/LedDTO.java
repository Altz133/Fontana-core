package com.fontana.backend.devices.led.dto;

import com.fontana.backend.devices.dto.DeviceDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LedDTO extends DeviceDTO {

    private byte colorR;
    private byte colorG;
    private byte colorB;
    private byte colorW;
    private byte power;
    private byte stroboscopeFrequency;

}
