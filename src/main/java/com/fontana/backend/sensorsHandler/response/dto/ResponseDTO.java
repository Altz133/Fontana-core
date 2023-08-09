package com.fontana.backend.sensorsHandler.response.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
    //TODO walidacja zapytak lukiego
    private int temperature;
    private int humidity;
    private int light;
    @JsonProperty("water_top")
    private boolean waterTop;
    @JsonProperty("water_bottom")
    private boolean waterBottom;

}
