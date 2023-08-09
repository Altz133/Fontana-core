package com.fontana.backend.devices.led.entity;

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
public class Led{
    @Min(value = 1)
    @Max(value = 512)
    private int ColorRID;
    @Min(value = Byte.MIN_VALUE)
    @Max(value = Byte.MAX_VALUE)
    private byte ColorRValue;
    @Min(value = 1)
    @Max(value = 512)
    private int ColorGID;
    @Min(value = Byte.MIN_VALUE)
    @Max(value = Byte.MAX_VALUE)
    private byte ColorGValue;
    @Min(value = 1)
    @Max(value = 512)
    private int ColorBID;
    @Min(value = Byte.MIN_VALUE)
    @Max(value = Byte.MAX_VALUE)
    private byte ColorBValue;
    @Min(value = 1)
    @Max(value = 512)
    private int ColorWID;
    @Min(value = Byte.MIN_VALUE)
    @Max(value = Byte.MAX_VALUE)
    private byte ColorWValue;
    @Min(value = 1)
    @Max(value = 512)
    private int DimmID;
    @Min(value = Byte.MIN_VALUE)
    @Max(value = Byte.MAX_VALUE)
    private byte DimmValue;
    @Min(value = 1)
    @Max(value = 512)
    private int StrobeFreqID;
    @Min(value = Byte.MIN_VALUE)
    @Max(value = Byte.MAX_VALUE)
    private byte StrobeFreqValue;

}
