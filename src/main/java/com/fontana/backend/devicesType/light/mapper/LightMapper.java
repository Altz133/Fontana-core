package com.fontana.backend.devicesType.light.mapper;

import com.fontana.backend.devicesType.led.dto.LedDTO;
import com.fontana.backend.devicesType.light.dto.LightDTO;
import com.fontana.backend.devicesType.light.entity.Light;

public class LightMapper {

    //FIXME mapper
    public Light DTOToLight(LightDTO lightDTO){
        return new Light();
    }

    public LightDTO LightToDTO(Light light){
        return new LightDTO();
    }

}
