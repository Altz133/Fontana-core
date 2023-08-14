package com.fontana.backend.log.service;

import com.fontana.backend.log.dto.LogResponseDTO;

import java.util.List;

public interface LogService {

    List<LogResponseDTO> findAll(String username, Integer sessionId);

    LogResponseDTO findById(int id);
}
