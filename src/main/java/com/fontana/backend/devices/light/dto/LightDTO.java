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
    @Min(value = 0)
    @Max(value = 255)
    private byte colorR;
    @Min(value = 0)
    @Max(value = 255)
    private byte colorG;
    @Min(value = 0)
    @Max(value = 255)
    private byte colorB;


}
