package com.fontana.backend.log.controller;

import com.fontana.backend.log.dto.LogResponseDTO;
import com.fontana.backend.log.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.fontana.backend.config.RestEndpoints.LOG;

@RestController
@RequestMapping(LOG)
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;

    @GetMapping()
    public List<LogResponseDTO> findAll(
            @RequestParam(name = "username", required = false) String username,
            @RequestParam(name = "sessionId", required = false) Integer sessionId) {
        return logService.findAll(username, sessionId);
    }
}
