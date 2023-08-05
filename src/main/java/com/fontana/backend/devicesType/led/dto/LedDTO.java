package com.fontana.backend.devicesType.led.dto;

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

}
