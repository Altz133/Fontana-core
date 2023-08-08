package com.fontana.backend.devices.pump.entity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pump {
    @Min(value = 1)
    @Max(value = 512)
    private int id;
    @Min(value = 0)
    @Max(value = 255)
    private byte value;

}
