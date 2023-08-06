package com.fontana.backend.devicesType.led.dto;

import com.fontana.backend.devicesType.led.entity.Led;
import lombok.Data;
import org.springframework.stereotype.Component;


@Data
public class LedDTO {

    private String name;
    private int colorR;
    private int colorG;
    private int colorB;
    private int colorW;
    private int power;
    private boolean stroboscopeEnabled;
    private int stroboscopeFrequency;

    public LedDTO() {
    }

    public LedDTO(String name, int colorR, int colorG, int colorB, int colorW, int power, boolean stroboscopeEnabled, int stroboscopeFrequency) {
        this.name = name;
        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
        this.colorW = colorW;
        this.power = power;
        this.stroboscopeEnabled = stroboscopeEnabled;
        this.stroboscopeFrequency = stroboscopeFrequency;
    }

}
