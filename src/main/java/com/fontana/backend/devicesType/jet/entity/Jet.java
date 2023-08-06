package com.fontana.backend.devicesType.jet.entity;

import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
@Table(name="Jet")
public class Jet {

    private int id;
    private byte value;
    public Jet() {
    }
    public Jet(int id, byte value) {
        this.id = id;
        this.value = value;
    }
}
