package com.fontana.backend.devicesType.light.entity;

import lombok.Data;

@Data
public class Light {

    private int ColorRID;
    private byte ColorRValue;
    private int ColorGID;
    private byte ColorGValue;
    private int ColorBID;
    private byte ColorBValue;

    public Light() {
    }

    public Light(int ColorRID, byte ColorRValue, int ColorGID, byte ColorGValue, int ColorBID, byte ColorBValue) {
        this.ColorRID = ColorRID;
        this.ColorRValue = ColorRValue;
        this.ColorGID = ColorGID;
        this.ColorGValue = ColorGValue;
        this.ColorBID = ColorBID;
        this.ColorBValue = ColorBValue;
    }

}
