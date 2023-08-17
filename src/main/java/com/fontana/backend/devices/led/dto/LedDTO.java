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
    private int colorR;

    @Min(value = Byte.MIN_VALUE)
    @Max(value = Byte.MAX_VALUE)
    private int colorG;

    @Min(value = Byte.MIN_VALUE)
    @Max(value = Byte.MAX_VALUE)
    private int colorB;

    @Min(value = Byte.MIN_VALUE)
    @Max(value = Byte.MAX_VALUE)
    private int colorW;

    @Min(value = Byte.MIN_VALUE)
    @Max(value = Byte.MAX_VALUE)
    private int power;

    @Min(value = Byte.MIN_VALUE)
    @Max(value = Byte.MAX_VALUE)
    private int stroboscopeFrequency;

    public LedDTO(String name,
                  byte colorR,
                  byte colorG,
                  byte colorB,
                  byte colorW,
                  byte power,
                  byte stroboscopeFrequency) {

        super(name);
        this.colorR = colorR & 0xFF;
        this.colorG = colorG & 0xFF;
        this.colorB = colorB & 0xFF;
        this.colorW = colorW & 0xFF;
        this.power = power & 0xFF;
        this.stroboscopeFrequency = stroboscopeFrequency & 0xFF;
    }
}

