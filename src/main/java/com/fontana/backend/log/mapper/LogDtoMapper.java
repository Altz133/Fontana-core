package com.fontana.backend.log.mapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import com.fontana.backend.devices.entity.DeviceType;
import com.fontana.backend.log.dto.LogRequestDTO;
import com.fontana.backend.log.dto.LogResponseDTO;
import com.fontana.backend.log.entity.Log;
import com.fontana.backend.user.entity.User;
import com.fontana.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.*;

@Service
@RequiredArgsConstructor
public class LogDtoMapper {

    private final UserRepository userRepository;

    public LogResponseDTO map(Log log) {
        User user = userRepository.findByUsername(log.getUsername()).orElse(null);
        String fullName = user != null ? user.getFirstName().concat(" " + user.getLastName()) : null;

        String username = user != null ?
                user.getFirstName().concat(" " + user.getLastName()) :
                null;

        return LogResponseDTO.builder()
                .id(log.getId())
                .sessionId(log.getSessionId())
                .username(fullName)
                .executedAt(log.getExecutedAt())
                .deviceType(log.getDeviceType())
                .deviceName(log.getDeviceName())
                .deviceValue(log.getDeviceValue())
                .build();
    }

    public Log map(LogRequestDTO logRequestDTO) {
        Date date = logRequestDTO.getExecutedAt();
        LocalDateTime localDateTime = null;
        if (date != null) {
            localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        }

        return Log.builder()
                .username(logRequestDTO.getUsername())
                .sessionId(logRequestDTO.getSessionId())
                .executedAt(localDateTime)
                .deviceName(logRequestDTO.getDeviceName())
                .deviceType(DeviceType.valueOf(logRequestDTO.getDeviceType()))
                .deviceValue(logRequestDTO.getDeviceValue())
                .build();
    }
}
