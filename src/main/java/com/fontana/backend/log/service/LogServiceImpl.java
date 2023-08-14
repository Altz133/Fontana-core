package com.fontana.backend.log.service;

import com.fontana.backend.log.dto.LogResponseDTO;
import com.fontana.backend.log.entity.Log;
import com.fontana.backend.log.mapper.LogDtoMapper;
import com.fontana.backend.log.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;
    private final LogDtoMapper logDtoMapper;
    @Override
    public List<LogResponseDTO> findAll() {
        List<Log> logs = logRepository.findAll();

        return logs.stream()
                .map(logDtoMapper::map)
                .toList();
    }
}