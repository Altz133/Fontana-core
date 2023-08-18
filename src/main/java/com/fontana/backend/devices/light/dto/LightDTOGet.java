package com.fontana.backend.devices.light.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class LightDTOGet {
    @Min(value = Byte.MIN_VALUE)
    @Max(value = Byte.MAX_VALUE)
    private byte colorR;

    @Min(value = Byte.MIN_VALUE)
    @Max(value = Byte.MAX_VALUE)
    private byte colorG;

    @Min(value = Byte.MIN_VALUE)
    @Max(value = Byte.MAX_VALUE)
    private byte colorB;

    public LightDTO(String name, byte ColorR, byte ColorG, byte ColorB) {
        super(name);
        this.colorR = ColorR & 0xFF;
        this.colorG = ColorG & 0xFF;
        this.colorB = ColorB & 0xFF;
    }
}
