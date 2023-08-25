package com.fontana.backend.session.service;

import com.fontana.backend.exception.customExceptions.NotFoundException;
import com.fontana.backend.session.dto.SessionResponseDTO;
import com.fontana.backend.session.dto.SessionWatcherRequestDTO;
import com.fontana.backend.session.entity.Session;
import com.fontana.backend.session.entity.SessionWatcher;
import com.fontana.backend.session.repository.SessionRepository;
import com.fontana.backend.session.repository.SessionWatcherRepository;
import com.fontana.backend.user.entity.User;
import com.fontana.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    @Value("${notification.already-displayed}")
    private String alreadyDisplayedMsg;

    @Value("${user.not-found-msg}")
    private String userNotFoundMsg;

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final SessionWatcherRepository watcherRepository;
    private final SessionService sessionService;

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
    public ResponseEntity<?> getNonDisplayedAmount(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new NotFoundException(userNotFoundMsg));

        List<Session> sessions = sessionService.filterSessionsInReversedOrder(user);

        Map<String, Integer> response = new HashMap<>();
        response.put("amount", sessions.size());
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<?> updateAllSessionsWatchers(SessionWatcherRequestDTO request) {
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> new NotFoundException(userNotFoundMsg));

        List<Session> nonDisplayedSession = sessionService.filterSessionsInReversedOrder(user);

        for (Session session : nonDisplayedSession) {
            SessionWatcher watcher = SessionWatcher.builder()
                    .sessionId(session.getId())
                    .watcher(request.getUsername())
                    .build();

            watcherRepository.save(watcher);
        }

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> findNonDisplayedAmount(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new NotFoundException(userNotFoundMsg));

        List<Session> sessions = sessionService.filterSessionsInReversedOrder(user);

        Map<String, Integer> response = new HashMap<>();
        response.put("amount", sessions.size());
        return ResponseEntity.ok().body(response);
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
