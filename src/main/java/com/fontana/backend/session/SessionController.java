package com.fontana.backend.session;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.fontana.backend.config.RestEndpoints.SESSION;

@RestController
@RequestMapping(SESSION)
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @GetMapping()
    public List<Session> findAll() {
        return sessionService.findAll();
    }

    @GetMapping(value = "/{id}")
    public Session findById(@PathVariable("id") Integer id) {
        return sessionService.findById(id);
    }

    @PostMapping()
    public ResponseEntity<?> add(@RequestBody @Validated SessionDTO sessionDTO) {
        return sessionService.add(sessionDTO);
    }

    @PutMapping(value = "/close")
    public ResponseEntity<?> updateCloseSession(@RequestBody SessionCloseRequest sessionCloseRequest) {
        return sessionService.updateCloseSession(sessionCloseRequest);
    }

}
