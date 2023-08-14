package com.fontana.backend.log.service;

import com.fontana.backend.exception.customExceptions.NotFoundException;
import com.fontana.backend.log.dto.LogResponseDTO;
import com.fontana.backend.log.entity.Log;
import com.fontana.backend.log.mapper.LogDtoMapper;
import com.fontana.backend.log.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogServiceImpl implements LogService {

    @Value("${log.not-found-msg}")
    private String notFoundMsg;

    private final LogRepository logRepository;
    private final LogDtoMapper logDtoMapper;

    @Override
    public List<LogResponseDTO> findAll(String username, Integer sessionId) {
        List<Log> logs = logRepository.findAll();

        if (username != null) {
            logs = logRepository.findAllByUsername(username);
            log.info("Logs by username: " + logs);
        }

        if (sessionId != null) {
            logs = logRepository.findAllBySessionId(sessionId);
        }

        return logs.stream()
                .map(logDtoMapper::map)
                .toList();
    }

    @Override
    public LogResponseDTO findById(int id) {
        Log searched = logRepository.findById(id).orElseThrow(
                () -> new NotFoundException(notFoundMsg));

        return logDtoMapper.map(searched);
    }
}
