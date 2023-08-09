package com.fontana.backend.sensorsHandler.sensors.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Sensors {

        private Boolean waterTop;
        private Boolean waterBottom;


}
