package com.fontana.backend.snapshot.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Snapshot {

    private int id;
    private Byte value;

    Snapshot(){
        this.id = 0;
        this.value = 0;
    }

}