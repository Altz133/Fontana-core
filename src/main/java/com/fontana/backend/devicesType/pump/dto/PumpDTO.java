package com.fontana.backend.devicesType.pump.dto;

import lombok.Data;

@Data
public class PumpDTO {
    private String name;
    private byte value;

    public PumpDTO() {
    }

    public PumpDTO(String name, byte value) {
        this.name = name;
        this.value = value;
    }

}
