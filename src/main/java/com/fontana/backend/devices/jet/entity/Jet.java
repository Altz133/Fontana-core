package com.fontana.backend.devices.jet.entity;

import com.fontana.backend.devices.entity.Device;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
@Component
public class Jet extends Device {

    @Min(value = 0)
    @Max(value = 511)
    private Integer id;

    @Min(value = Byte.MIN_VALUE)
    @Max(value = Byte.MAX_VALUE)
    private byte value;

}
