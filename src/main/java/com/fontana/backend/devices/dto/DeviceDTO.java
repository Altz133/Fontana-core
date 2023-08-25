package com.fontana.backend.devices.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class DeviceDTO {

    @NotEmpty
    private String name;
}
