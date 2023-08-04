package com.fontana.backend.devicesType.led.mapper;

import com.fontana.backend.devicesType.led.dto.LedDTO;
import com.fontana.backend.devicesType.led.entity.Led;
import org.springframework.stereotype.Service;

@Service
public class LedMapper {

        public LedMapper() {
        }
        //FIXME - Mapper potrzebny
        public LedDTO LedToDTO(Led led) {
            return new LedDTO();
        }

        public Led DTOToLed(LedDTO ledDTO) { return new Led(); }
}
