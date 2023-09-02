package com.fontana.backend.log.service;

import com.fontana.backend.exception.customExceptions.NotFoundException;
import com.fontana.backend.log.dto.LogRequestDTO;
import com.fontana.backend.log.dto.LogResponseDTO;
import com.fontana.backend.log.entity.Log;
import com.fontana.backend.log.mapper.LogDtoMapper;
import com.fontana.backend.log.repository.LogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LogServiceImplTest {

    @Mock
    private LogRepository logRepository;

    @Mock
    private LogDtoMapper logDtoMapper;

    @InjectMocks
    private LogServiceImpl logService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        Log log = new Log();
        LogResponseDTO logResponseDTO = new LogResponseDTO();
        Page<Log> logPage = new PageImpl<>(Collections.singletonList(log));
        when(logRepository.findAll(any(Pageable.class))).thenReturn(logPage);
        when(logDtoMapper.map(log)).thenReturn(logResponseDTO);

        Page<LogResponseDTO> result = logService.findAll(null, null, Pageable.unpaged());

        assertEquals(1, result.getContent().size());
        assertEquals(logResponseDTO, result.getContent().get(0));
    }

    @Test
    void findById() {
        Log log = new Log();
        LogResponseDTO logResponseDTO = new LogResponseDTO();
        when(logRepository.findById(anyInt())).thenReturn(Optional.of(log));
        when(logDtoMapper.map(log)).thenReturn(logResponseDTO);

        LogResponseDTO result = logService.findById(1);

        assertEquals(logResponseDTO, result);
    }

    @Test
    void findById_notFound() {
        when(logRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> logService.findById(1));
    }

    @Test
    void add() {
        Log log = new Log();
        log.setId(1);  // <-- Make sure to set an ID.
        LogRequestDTO logRequestDTO = new LogRequestDTO();
        when(logDtoMapper.map(logRequestDTO)).thenReturn(log);
        when(logRepository.save(log)).thenReturn(log);

        ResponseEntity<?> result = logService.add(logRequestDTO);

        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    void findAllLogs() {
        Log log = new Log();
        LogResponseDTO logResponseDTO = new LogResponseDTO();
        when(logRepository.findAll()).thenReturn(Collections.singletonList(log));
        when(logDtoMapper.map(log)).thenReturn(logResponseDTO);

        List<LogResponseDTO> result = logService.findAllLogs();

        assertEquals(1, result.size());
        assertEquals(logResponseDTO, result.get(0));
    }

    @Test
    void downloadAllLogs() {
        Log log = new Log() {
            @Override
            public String toString() {
                return "logString";
            }
        };
        when(logRepository.findAll()).thenReturn(Collections.singletonList(log));

        byte[] result = logService.downloadAllLogs();

        assertEquals("logString\n", new String(result));
    }
}
