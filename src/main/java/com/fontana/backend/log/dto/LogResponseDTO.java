package com.fontana.backend.log.dto;

import com.fontana.backend.devices.entity.DeviceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogResponseDTO {

    private Integer id;
    private String username;
    private Integer sessionId;
    private LocalDateTime executedAt;
    private DeviceType deviceType;
    private String deviceName;
    private String deviceValue;
}
