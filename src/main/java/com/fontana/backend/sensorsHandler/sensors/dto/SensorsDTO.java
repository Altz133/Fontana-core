package com.fontana.backend.sensorsHandler.sensors.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorsDTO {

    @JsonProperty("water_top")
    private Boolean waterTop;
    @JsonProperty("water_bottom")
    private Boolean waterBottom;

}
