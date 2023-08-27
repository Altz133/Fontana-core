package com.fontana.backend.log.service;

import com.fontana.backend.log.dto.LogRequestDTO;
import com.fontana.backend.log.dto.LogResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface LogService {

    Page<LogResponseDTO> findAll(String username, Integer sessionId, Pageable pageable);
    LogResponseDTO findById(int id);
    ResponseEntity<?> add(LogRequestDTO logRequestDTO);
    List<LogResponseDTO> findAllLogs();
    byte[] downloadAllLogs();

}