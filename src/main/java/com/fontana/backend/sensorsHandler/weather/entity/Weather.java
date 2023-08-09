package com.fontana.backend.sensorsHandler.weather.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Weather {

    private int temperature;
    private int humidity;
    private int light;
}
