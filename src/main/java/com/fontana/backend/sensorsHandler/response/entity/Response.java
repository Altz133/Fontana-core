package com.fontana.backend.sensorsHandler.response.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    private int temperature;
    private int humidity;
    private int light;

    private boolean waterTop;
    private boolean waterBottom;

}
