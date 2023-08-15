package com.fontana.backend.devices.led.dto;

import com.fontana.backend.devices.dto.DeviceDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LedDTO extends DeviceDTO {

    @Min(value = Byte.MIN_VALUE)
    @Max(value = Byte.MAX_VALUE)
    private byte colorR;

    @Min(value = Byte.MIN_VALUE)
    @Max(value = Byte.MAX_VALUE)
    private byte colorG;

    @Min(value = Byte.MIN_VALUE)
    @Max(value = Byte.MAX_VALUE)
    private byte colorB;

    @Min(value = Byte.MIN_VALUE)
    @Max(value = Byte.MAX_VALUE)
    private byte colorW;

    @Min(value = Byte.MIN_VALUE)
    @Max(value = Byte.MAX_VALUE)
    private byte power;

    @Min(value = Byte.MIN_VALUE)
    @Max(value = Byte.MAX_VALUE)
    private byte stroboscopeFrequency;

}
