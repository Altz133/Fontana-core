package com.fontana.backend.session;

import com.fontana.backend.exception.customExceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.fontana.backend.config.RestEndpoints.SESSION;

@Service
@RequiredArgsConstructor
@Slf4j
public class SessionServiceImpl implements SessionService {

    private static final String notFoundMsg = "Cannot found session with id:";

    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;

    @Override
    public List<Session> findAll() {
        return sessionRepository.findAll();
    }

    @Override
    public Session findById(Integer id) {
        return sessionRepository.findById(id).orElseThrow(
                () -> new NotFoundException(notFoundMsg.concat(" " + id)));
    }

    @Override
    public ResponseEntity<?> add(SessionDTO sessionDTO) {
        Session saved = sessionRepository.save(sessionMapper.map(sessionDTO));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, SESSION.concat("/").concat(saved.getId().toString()));

        return ResponseEntity.status(HttpStatus.NO_CONTENT).headers(headers).body(null);
    }

    @Override
    public ResponseEntity<?> updateCloseSession(Integer id, LocalDateTime requestedClosedTime) {
        Session existing = findById(id);

        Session updated = Session.builder()
                .id(existing.getId())
                .username(existing.getUsername())
                .openedTime(existing.getOpenedTime())
                .closedTime(requestedClosedTime)
                .build();

        sessionRepository.save(updated);
        return ResponseEntity.noContent().build();
    }
}
