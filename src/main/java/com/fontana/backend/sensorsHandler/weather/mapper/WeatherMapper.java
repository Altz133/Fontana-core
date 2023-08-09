package com.fontana.backend.sensorsHandler.weather.mapper;

import com.fontana.backend.sensorsHandler.weather.dto.WeatherDTO;
import com.fontana.backend.sensorsHandler.weather.entity.Weather;

public class WeatherMapper {

    public WeatherDTO weatherToDto(Weather weather){
        return new WeatherDTO(weather.getTemperature(), weather.getHumidity(), weather.getLight());
    }

    public Weather dtoToWeather(WeatherDTO weatherDTO){
        return new Weather(weatherDTO.getTemperature(), weatherDTO.getHumidity(), weatherDTO.getLight());
    }
}
