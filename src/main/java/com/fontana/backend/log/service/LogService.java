package com.fontana.backend.log.service;

import com.fontana.backend.log.dto.LogRequestDTO;
import com.fontana.backend.log.dto.LogResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LogService {

    List<LogResponseDTO> findAll(String username, Integer sessionId);

    LogResponseDTO findById(int id);

    ResponseEntity<?> add(LogRequestDTO logRequestDTO);
}
