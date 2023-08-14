package com.fontana.backend.log.mapper;

import com.fontana.backend.log.dto.LogResponseDTO;
import com.fontana.backend.log.entity.Log;
import org.springframework.stereotype.Service;

@Service
public class LogDtoMapper {

    public LogResponseDTO map(Log log) {

        String userFullName = log.getUser().getFirstName().concat(" " + log.getUser().getLastName());

        return LogResponseDTO.builder()
                .id(log.getId())
                .userFullName(userFullName)
                .sessionId(log.getSessionId())
                .executedAt(log.getExecutedAt())
                .deviceValue(log.getDeviceValue())
                .build();
    }
}
