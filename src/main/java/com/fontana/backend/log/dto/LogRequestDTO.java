package com.fontana.backend.log.dto;

import com.fontana.backend.devices.entity.DeviceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogRequestDTO {

    @NotBlank
    private String username;

    @NotEmpty
    private Integer sessionId;

    @NotEmpty
    @PastOrPresent
    private LocalDateTime executedAt;

    @NotBlank
    private DeviceType deviceType;

    @NotEmpty
    private String deviceValue;
}
