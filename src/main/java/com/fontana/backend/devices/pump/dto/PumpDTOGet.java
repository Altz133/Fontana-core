package com.fontana.backend.devices.pump.dto;

import com.fontana.backend.devices.dto.DeviceDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PumpDTOGet extends DeviceDTO {

    @Min(value = 0)
    @Max(value = 255)
    private int value;

    public PumpDTOGet(String name, byte value) {
        super(name);
        this.value = value & 0xFF;
    }
}
