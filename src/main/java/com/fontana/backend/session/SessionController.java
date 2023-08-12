package com.fontana.backend.session;

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

    @GetMapping()
    public List<Session> findAll() {
        return sessionService.findAll();
    }

    @GetMapping(SESSION_FIND_BY_ID)
    public Session findById(@PathVariable("id") Integer id) {
        return sessionService.findById(id);
    }

//    @GetMapping(SESSION_CHECK)
//    public ResponseEntity<?> checkIsActive(@PathVariable("username") String username) {
//        return sessionService.checkIsActive(username);
//    }

    @PostMapping()
    public ResponseEntity<?> add(@RequestBody @Validated SessionDTO sessionDTO) {
        return sessionService.add(sessionDTO);
    }

    @PutMapping(SESSION_UPDATE_CLOSE)
    public ResponseEntity<?> updateCloseSession(@RequestBody @Validated SessionCloseRequest sessionCloseRequest) {
        return sessionService.updateCloseSession(sessionCloseRequest);
    }
}
