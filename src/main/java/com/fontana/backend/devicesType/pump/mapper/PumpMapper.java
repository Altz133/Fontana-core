package com.fontana.backend.devicesType.pump.mapper;

import com.fontana.backend.devicesType.pump.dto.PumpDTO;
import com.fontana.backend.devicesType.pump.entity.Pump;
import org.springframework.stereotype.Service;

@Service
public class PumpMapper {

    public Pump DTOToPump(PumpDTO pumpDTO){
        /*FIXME TU BEDZIE MAPPER*/
        return new Pump();
    }

    public PumpDTO PumpToDTO(Pump pump){
        return new PumpDTO();
    }

}

