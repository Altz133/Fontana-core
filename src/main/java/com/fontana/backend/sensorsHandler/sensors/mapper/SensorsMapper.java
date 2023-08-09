package com.fontana.backend.sensorsHandler.sensors.mapper;

import com.fontana.backend.sensorsHandler.sensors.dto.SensorsDTO;
import com.fontana.backend.sensorsHandler.sensors.entity.Sensors;
import org.springframework.stereotype.Service;

@Service
public class SensorsMapper {
    public SensorsDTO sensorsToDto(Sensors sensors){
        return new SensorsDTO(sensors.getWaterTop(), sensors.getWaterBottom());
    }

    public Sensors dtoToSensors(SensorsDTO sensorsDTO){
        return new Sensors(sensorsDTO.getWaterTop(), sensorsDTO.getWaterBottom());
    }

}
