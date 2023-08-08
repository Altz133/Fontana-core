package com.fontana.backend.devices.led.dto;

import com.fontana.backend.devices.dto.DeviceDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LedDTO extends DeviceDTO {
    @Min(value = 0)
    @Max(value = 255)
    private byte colorR;
    @Min(value = 0)
    @Max(value = 255)
    private byte colorG;
    @Min(value = 0)
    @Max(value = 255)
    private byte colorB;
    @Min(value = 0)
    @Max(value = 255)
    private byte colorW;
    @Min(value = 0)
    @Max(value = 255)
    private byte power;
    @Min(value = 0)
    @Max(value = 255)
    private byte stroboscopeFrequency;

}
