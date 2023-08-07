package com.fontana.backend.devices.light.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Light {

    private int ColorRID;
    private byte ColorRValue;
    private int ColorGID;
    private byte ColorGValue;
    private int ColorBID;
    private byte ColorBValue;

}
