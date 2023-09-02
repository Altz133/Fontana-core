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
public class LedDTOGet extends DeviceDTO {
    @Min(value = 0)
    @Max(value = 255)
    private int colorR;

    @Min(value = 0)
    @Max(value = 255)
    private int colorG;

    @Min(value = 0)
    @Max(value = 255)
    private int colorB;

    @Min(value = 0)
    @Max(value = 255)
    private int colorW;

    @Min(value = 0)
    @Max(value = 255)
    private int power;

    @Min(value = 0)
    @Max(value = 255)

    private int stroboscopeFrequency;

    public LedDTOGet(String name,
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
