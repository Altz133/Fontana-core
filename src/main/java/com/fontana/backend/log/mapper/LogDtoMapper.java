package com.fontana.backend.log.mapper;

import com.fontana.backend.log.dto.LogResponseDTO;
import com.fontana.backend.log.entity.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogDtoMapper {

    public LogResponseDTO map(Log log) {

        return LogResponseDTO.builder()
                .id(log.getId())
                .sessionId(log.getSessionId())
                .executedAt(log.getExecutedAt())
                .deviceValue(log.getDeviceValue())
                .build();
    }
}
