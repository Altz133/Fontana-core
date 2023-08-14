package com.fontana.backend.log.service;

import com.fontana.backend.log.dto.LogResponseDTO;
import com.fontana.backend.log.entity.Log;

import java.util.List;

public interface LogService {

    List<LogResponseDTO> findAll();
}
