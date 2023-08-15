package com.fontana.backend.log.controller;

import com.fontana.backend.log.dto.LogRequestDTO;
import com.fontana.backend.log.dto.LogResponseDTO;
import com.fontana.backend.log.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fontana.backend.config.RestEndpoints.LOG;
import static com.fontana.backend.config.RestEndpoints.LOG_FIND_BY_ID;

@RestController
@RequestMapping(LOG)
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;

    /**
     * To use queryParam, just add ?param_name=value to main http path. For example: ?username=fontanna_admin.
     */
    @GetMapping()
    public List<LogResponseDTO> findAll(
            @RequestParam(name = "username", required = false) String username,
            @RequestParam(name = "sessionId", required = false) Integer sessionId) {
        return logService.findAll(username, sessionId);
    }

    @GetMapping(LOG_FIND_BY_ID)
    public LogResponseDTO findById(@PathVariable("id") int id) {
        return logService.findById(id);
    }

    @PostMapping()
    public ResponseEntity<?> add(@RequestBody LogRequestDTO logRequestDTO) {
        return logService.add(logRequestDTO);
    }
}
