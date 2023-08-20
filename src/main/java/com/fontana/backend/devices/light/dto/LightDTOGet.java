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
public class LightDTOGet extends DeviceDTO {
    @Min(value = 0)
    @Max(value = 255)
    private int colorR;

    @Min(value = 0)
    @Max(value = 255)
    private int colorG;

    @Min(value = 0)
    @Max(value = 255)
    private int colorB;

    public LightDTOGet(String name, int ColorR, int ColorG, int ColorB) {
        super(name);
        this.colorR = ColorR & 0xFF;
        this.colorG = ColorG & 0xFF;
        this.colorB = ColorB & 0xFF;
    }
}
