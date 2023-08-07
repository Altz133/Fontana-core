package com.fontana.backend.devices.led.entity;

import com.fontana.backend.devices.dto.DeviceDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Led extends DeviceDTO {

    private int ColorRID;
    private byte ColorRValue;
    private int ColorGID;
    private byte ColorGValue;
    private int ColorBID;
    private byte ColorBValue;
    private int ColorWID;
    private byte ColorWValue;

    private int DimmID;
    private byte DimmValue;

    private int StrobeFreqID;
    private byte StrobeFreqValue;

}
