package com.fontana.backend.sensorsHandler.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDTO {

    private int temperature;
    private int humidity;
    private int light;
}
