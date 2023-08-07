package com.fontana.backend.devicesType.led.dto;

import lombok.Data;


@Data
public class LedDTO {

    private String name;
    private byte colorR;
    private byte colorG;
    private byte colorB;
    private byte colorW;
    private byte power;
    private byte stroboscopeFrequency;

    public LedDTO() {
    }

    public LedDTO(String name, byte colorR, byte colorG, byte colorB, byte colorW, byte power, byte stroboscopeFrequency) {
        this.name = name;
        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
        this.colorW = colorW;
        this.power = power;
        this.stroboscopeFrequency = stroboscopeFrequency;
    }

}
