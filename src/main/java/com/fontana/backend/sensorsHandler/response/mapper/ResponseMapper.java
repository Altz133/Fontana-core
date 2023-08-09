package com.fontana.backend.sensorsHandler.response.mapper;

import com.fontana.backend.sensorsHandler.response.dto.ResponseDTO;
import com.fontana.backend.sensorsHandler.response.entity.Response;
import org.springframework.stereotype.Service;

@Service
public class ResponseMapper {

    public Response dtoToEntity(ResponseDTO responseDTO) {
        return new Response(responseDTO.getTemperature(), responseDTO.getHumidity(), responseDTO.getLight(), responseDTO.isWaterTop(), responseDTO.isWaterBottom());
    }

    public ResponseDTO entityToDto(Response response) {
        return new ResponseDTO(response.getTemperature(), response.getHumidity(), response.getLight(), response.isWaterTop(), response.isWaterBottom());
    }

}
