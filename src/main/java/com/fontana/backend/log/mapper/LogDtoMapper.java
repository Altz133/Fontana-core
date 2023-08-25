package com.fontana.backend.log.mapper;

import com.fontana.backend.log.dto.LogRequestDTO;
import com.fontana.backend.log.dto.LogResponseDTO;
import com.fontana.backend.log.entity.Log;
import com.fontana.backend.user.entity.User;
import com.fontana.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogDtoMapper {

    private final UserRepository userRepository;

    public LogResponseDTO map(Log log) {

        User user = userRepository.findByUsername(log.getUsername()).orElse(null);

        String fullName = user != null ?
                user.getFirstName().concat(" " + user.getLastName()) :
                null;

        return LogResponseDTO.builder()
                .id(log.getId())
                .sessionId(log.getSessionId())
                .userFullName(fullName)
                .executedAt(log.getExecutedAt())
                .deviceType(log.getDeviceType())
                .deviceValue(log.getDeviceValue())
                .build();
    }

    public Log map(LogRequestDTO logRequestDTO) {

        return Log.builder()
                .username(logRequestDTO.getUsername())
                .sessionId(logRequestDTO.getSessionId())
                .executedAt(logRequestDTO.getExecutedAt())
                .deviceType(logRequestDTO.getDeviceType())
                .deviceValue(logRequestDTO.getDeviceValue())
                .build();
    }
}