package com.fontana.backend.devicesType.light.dto;

import lombok.Data;

@Data
public class LightDTO {

    private String name;
    private int colorR;
    private int colorG;
    private int colorB;

    public LightDTO() {
    }

    public LightDTO(String name, int colorR, int colorG, int colorB) {
        this.name = name;
        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

}
