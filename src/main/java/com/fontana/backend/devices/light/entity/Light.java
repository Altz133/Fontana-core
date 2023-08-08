package com.fontana.backend.devices.light.entity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Light {
    @Min(value = 1)
    @Max(value = 512)
    private int ColorRID;
    @Min(value = 0)
    @Max(value = 255)
    private byte ColorRValue;
    @Min(value = 1)
    @Max(value = 512)
    private int ColorGID;
    @Min(value = 0)
    @Max(value = 255)
    private byte ColorGValue;
    @Min(value = 1)
    @Max(value = 512)
    private int ColorBID;
    @Min(value = 0)
    @Max(value = 255)
    private byte ColorBValue;

}
