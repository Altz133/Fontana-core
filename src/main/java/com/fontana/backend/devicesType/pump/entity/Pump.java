package com.fontana.backend.devicesType.pump.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Pump {
    private int id;
    private byte value;

    Pump() {
    }
    public Pump(int id, byte value) {
        this.id = id;
        this.value = value;
    }
}
