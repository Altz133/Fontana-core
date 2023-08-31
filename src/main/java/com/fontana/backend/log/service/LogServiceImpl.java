package com.fontana.backend.log.service;

import com.fontana.backend.exception.customExceptions.NotFoundException;
import com.fontana.backend.log.dto.LogRequestDTO;
import com.fontana.backend.log.dto.LogResponseDTO;
import com.fontana.backend.log.entity.Log;
import com.fontana.backend.log.mapper.LogDtoMapper;
import com.fontana.backend.log.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;


import static com.fontana.backend.config.RestEndpoints.LOG;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogServiceImpl implements LogService {

        private final LogRepository logRepository;
        private final LogDtoMapper logDtoMapper;
        @Value("${log.not-found-msg}")
        private String notFoundMsg;

        @Override
        public Page<LogResponseDTO> findAll(String username, Integer sessionId, Pageable pageable) {
            Page<Log> logs = logRepository.findAll(pageable);

            if (username != null) {
                logs = logRepository.findAllByUsername(username, pageable);
                log.info("Logs by username: " + logs);
            }

            if (sessionId != null) {
                // Adjust this line as per the new method signature in the repository
                logs = logRepository.findAllBySessionId(sessionId, pageable);
            }

            return logs.map(logDtoMapper::map);
        }

        @Override
        public LogResponseDTO findById(int id) {
            Log searched = logRepository.findById(id).orElseThrow(
                    () -> new NotFoundException(notFoundMsg));

            return logDtoMapper.map(searched);
        }

        @Override
        public ResponseEntity<?> add(LogRequestDTO logRequestDTO) {
            Log savedLog = logRepository.save(logDtoMapper.map(logRequestDTO));

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.LOCATION, LOG.concat("/").concat(savedLog.getId().toString()));

            return ResponseEntity.ok().headers(headers).build();
        }
        @Override
        public List<LogResponseDTO> findAllLogs() {
            List<Log> logs = logRepository.findAll();

            return logs.stream()
                    .map(logDtoMapper::map)
                    .collect(Collectors.toList());
        }
        @Override
        public byte[] downloadAllLogs() {
            List<Log> logs = logRepository.findAll();
            StringBuilder content = new StringBuilder();

            for (Log log : logs) {
                content.append(log.toString()).append("\n");
            }

            return content.toString().getBytes();
        }

}
