package com.fontana.backend.session.controller;

import com.fontana.backend.session.dto.SessionCloseRequest;
import com.fontana.backend.session.dto.SessionRequestDTO;
import com.fontana.backend.session.dto.SessionResponseDTO;
import com.fontana.backend.session.dto.SessionWatcherRequestDTO;
import com.fontana.backend.session.service.NotificationService;
import com.fontana.backend.session.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fontana.backend.config.RestEndpoints.*;

@RestController
@RequestMapping(SESSION)
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;
    private final NotificationService notificationService;

    @GetMapping()
    public List<SessionResponseDTO> findAll(@RequestParam(name = "watcher", required = false) String watcher,
                                            @RequestParam(name = "size", required = false) Integer size) {
        return sessionService.findAll(watcher, size);
    }

    @GetMapping(SESSION_FIND_BY_ID)
    public SessionResponseDTO findById(@PathVariable("id") Integer id) {
        return sessionService.findById(id);
    }

    @GetMapping(SESSION_FIND_NON_DISPLAYED_AMOUNT)
    public ResponseEntity<?> findNonDisplayedAmount(@PathVariable("username") String username) {
        return notificationService.findNonDisplayedAmount(username);
    }

    @PostMapping()
    public ResponseEntity<?> add(@RequestBody @Validated SessionRequestDTO sessionRequestDTO) {
        return sessionService.add(sessionRequestDTO);
    }

    @PutMapping(SESSION_UPDATE_CLOSE)
    public ResponseEntity<?> updateCloseSession(@RequestBody @Validated SessionCloseRequest sessionCloseRequest) {
        return sessionService.updateCloseSession(sessionCloseRequest);
    }

    @PutMapping(SESSION_UPDATE_WATCHER)
    public ResponseEntity<?> updateSingleSessionWatcher(@RequestBody @Validated SessionWatcherRequestDTO request,
                                                        @PathVariable("sessionId") Integer id) {
        return notificationService.updateSingleSessionWatcher(id, request);
    }

    @PutMapping(SESSION_UPDATE_WATCHER_ALL)
    public ResponseEntity<?> updateAllSessionsWatchers(@RequestBody @Validated SessionWatcherRequestDTO request) {
        return notificationService.updateAllSessionsWatchers(request);
    }
}
