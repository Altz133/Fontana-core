package com.fontana.backend.session.service;

import com.fontana.backend.session.dto.SessionWatcherRequestDTO;
import com.fontana.backend.session.entity.Session;
import com.fontana.backend.session.entity.SessionWatcher;
import com.fontana.backend.session.repository.SessionRepository;
import com.fontana.backend.session.repository.SessionWatcherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    @Value("${notification.already-displayed}")
    private String alreadyDisplayedMsg;

    private final SessionRepository sessionRepository;
    private final SessionWatcherRepository watcherRepository;

    @Override
    public ResponseEntity<?> updateSingleSessionWatcher(Integer sessionId, SessionWatcherRequestDTO request) {
        Optional<Session> assignedSession = sessionRepository.findById(sessionId);

        if (assignedSession.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Session session = assignedSession.get();

        if (containsWatcher(session, request.getUsername())) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", alreadyDisplayedMsg);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        SessionWatcher watcher = buildSessionWatcher(sessionId, request);
        watcherRepository.save(watcher);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> updateAllSessionsWatcher(SessionWatcherRequestDTO request) {
        return null;
    }

    private SessionWatcher buildSessionWatcher(Integer sessionId, SessionWatcherRequestDTO request) {
        return SessionWatcher.builder()
                .sessionId(sessionId)
                .watcher(request.getUsername())
                .build();
    }

    private boolean containsWatcher(Session session, String username) {
        for (SessionWatcher watcher : session.getWatchers()) {
            if (watcher.getWatcher().equals(username)) {
                return true;
            }
        }
        return false;
    }
}
