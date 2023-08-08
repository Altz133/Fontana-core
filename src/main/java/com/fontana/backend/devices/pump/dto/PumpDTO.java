package com.fontana.backend.devices.pump.dto;

import com.fontana.backend.devices.dto.DeviceDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PumpDTO extends DeviceDTO {
    @Min(value = 0)
    @Max(value = 255)
    private byte value;

}
