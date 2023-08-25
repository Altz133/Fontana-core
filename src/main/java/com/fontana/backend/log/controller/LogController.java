package com.fontana.backend.log.controller;

import com.fontana.backend.log.dto.LogRequestDTO;
import com.fontana.backend.log.dto.LogResponseDTO;
import com.fontana.backend.log.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

    @GetMapping()
    public Page<LogResponseDTO> findAll(
            @RequestParam(name = "username", required = false) String username,
            @RequestParam(name = "sessionId", required = false) Integer sessionId,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return logService.findAll(username, sessionId, pageable);
    }

    @GetMapping(LOG_FIND_BY_ID)
    public LogResponseDTO findById(@PathVariable("id") int id) {
        return logService.findById(id);
    }

    @PostMapping()
    public ResponseEntity<?> add(@RequestBody LogRequestDTO logRequestDTO) {
        return logService.add(logRequestDTO);
    }

    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> downloadLogs(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<LogResponseDTO> pageOfLogs = logService.findAll(null, null, pageable);
        List<LogResponseDTO> logs = pageOfLogs.getContent(); // This gets the list of logs from the page

        String content = ""; // Convert this list to a CSV or any other format you prefer

        for (LogResponseDTO log : logs) {
            content += log.toString() + "\n"; // Assuming LogResponseDTO has a proper toString() method
        }

        byte[] bytes = content.getBytes();
        ByteArrayResource resource = new ByteArrayResource(bytes);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=logs.txt")
                .contentType(MediaType.TEXT_PLAIN)
                .body(resource);
    }
}
