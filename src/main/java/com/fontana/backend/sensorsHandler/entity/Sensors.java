package com.fontana.backend.sensorsHandler.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Sensors {

    @JsonProperty("water_top")
    private Boolean waterTop;
    @JsonProperty("water_bottom")
    private Boolean waterBottom;
}
