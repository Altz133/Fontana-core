package com.fontana.backend.devices.led.entity;

import com.fontana.backend.devices.dto.DeviceDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Led extends DeviceDTO {
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
    @Min(value = 1)
    @Max(value = 512)
    private int ColorWID;
    @Min(value = 0)
    @Max(value = 255)
    private byte ColorWValue;
    @Min(value = 1)
    @Max(value = 512)
    private int DimmID;
    @Min(value = 0)
    @Max(value = 255)
    private byte DimmValue;
    @Min(value = 1)
    @Max(value = 512)
    private int StrobeFreqID;
    @Min(value = 0)
    @Max(value = 255)
    private byte StrobeFreqValue;

}
