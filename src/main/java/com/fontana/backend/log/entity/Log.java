package com.fontana.backend.log.entity;

import com.fontana.backend.devices.entity.DeviceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private Integer sessionId;
    private LocalDateTime executedAt;

    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;

    private Integer deviceValue;
    private String deviceName;
}
