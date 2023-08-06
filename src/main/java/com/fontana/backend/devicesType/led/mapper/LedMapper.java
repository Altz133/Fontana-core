package com.fontana.backend.devicesType.led.mapper;

import com.fontana.backend.devices.entity.Device;
import com.fontana.backend.devices.repository.DeviceRepository;
import com.fontana.backend.devicesType.led.dto.LedDTO;
import com.fontana.backend.devicesType.led.entity.Led;
import org.springframework.stereotype.Service;

@Service
public class LedMapper {
        DeviceRepository deviceRepository;
        public LedMapper() {
        }
        //FIXME - Mapper potrzebny
        public LedDTO LedToDTO(Led led) {
                return new LedDTO();
        }

        public Led DTOToLed(LedDTO ledDTO) {
                Device device = deviceRepository.findByname(ledDTO.getName());
                //FIXME Trzeba sie dopytac jak to wyglada czy wartosc kazda ma osobny kanal
                return new Led(); }
}
