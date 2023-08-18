package com.fontana.backend.devices.light.dto;

import com.fontana.backend.devices.dto.DeviceDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LightDTO extends DeviceDTO {

    @Min(value = Byte.MIN_VALUE)
    @Max(value = Byte.MAX_VALUE)
    private int colorR;

    @Min(value = Byte.MIN_VALUE)
    @Max(value = Byte.MAX_VALUE)
    private int colorG;

    @Min(value = Byte.MIN_VALUE)
    @Max(value = Byte.MAX_VALUE)
    private int colorB;

    public LightDTO(String name, byte ColorR, byte ColorG, byte ColorB) {
        super(name);
        this.colorR = ColorR & 0xFF;
        this.colorG = ColorG & 0xFF;
        this.colorB = ColorB & 0xFF;
    }
}
