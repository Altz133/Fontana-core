package com.fontana.backend.devices.jet.entity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
@Component
public class Jet {
    @Min(value = 1)
    @Max(value = 512)
    private int id;
    @Min(value = 0)
    @Max(value = 255)
    private byte value;

}
