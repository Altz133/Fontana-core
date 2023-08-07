package com.fontana.backend.devicesType.light.dto;

import lombok.Data;

@Data
public class LightDTO {

    private String name;
    private byte colorR;
    private byte colorG;
    private byte colorB;

    public LightDTO() {
    }

    public LightDTO(String name, byte colorR, byte colorG, byte colorB) {
        this.name = name;
        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

}
