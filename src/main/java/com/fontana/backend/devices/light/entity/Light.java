package com.fontana.backend.devices.light.entity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
@Component
public class Light {
    @Min(value = 0)
    @Max(value = 511)
    private int ColorRID;
    @Min(value = Byte.MIN_VALUE)
    @Max(value = Byte.MAX_VALUE)
    private byte ColorRValue;
    @Min(value = 0)
    @Max(value = 511)
    private int ColorGID;
    @Min(value = Byte.MIN_VALUE)
    @Max(value = Byte.MAX_VALUE)
    private byte ColorGValue;
    @Min(value = 0)
    @Max(value = 511)
    private int ColorBID;
    @Min(value = Byte.MIN_VALUE)
    @Max(value = Byte.MAX_VALUE)
    private byte ColorBValue;

}
