package com.fontana.backend.session;

import com.fontana.backend.exception.customExceptions.NotFoundException;
import com.fontana.backend.exception.customExceptions.SessionNotModifiedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.fontana.backend.config.RestEndpoints.SESSION;

@Service
@RequiredArgsConstructor
@Slf4j
public class SessionServiceImpl implements SessionService {

    private static final String notFoundMsg = "Cannot found session with id:";
    private static final String sessionBusyMsg = "Live control is busy";
    private static final String notAllowedToCloseMsg = "Not allowed to close session.";
    private static final String sessionAlreadyClosedMsg = "Session is already closed.";

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
        Session activeSession = getActiveSession();

        if (activeSession != null) {
            SessionBusyResponse response = SessionBusyResponse.builder()
                    .message(sessionBusyMsg)
                    .activeUserName(activeSession.getUsername())
                    .activeSessionStartTime(activeSession.getOpenedTime())
                    .intercepted(LocalDateTime.now())
                    .build();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        Session saved = sessionRepository.save(sessionMapper.map(sessionDTO));
        log.info(saved.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, SESSION.concat("/").concat(saved.getId().toString()));

        return ResponseEntity.status(HttpStatus.OK).headers(headers).build();
    }

    @Override
    public ResponseEntity<?> updateCloseSession(SessionCloseRequest sessionCloseRequest) {
        Session activeSession = getActiveSession();
        log.info("Request:" + sessionCloseRequest);

        if (activeSession == null) {
            throw new SessionNotModifiedException(sessionAlreadyClosedMsg);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getPrincipal().toString();

        if (!activeSession.getUsername().equals(currentPrincipalName)) {
            throw new SessionNotModifiedException(notAllowedToCloseMsg);
        }

        Session updated = Session.builder()
                .id(activeSession.getId())
                .username(activeSession.getUsername())
                .openedTime(activeSession.getOpenedTime())
                .closedTime(sessionCloseRequest.getClosedTime())
                .build();
        log.info("Updated:" + updated);

        sessionRepository.save(updated);
        return ResponseEntity.ok().build();
    }

    private Session getActiveSession() {
        List<Session> activeSessions = sessionRepository.findAll().stream()
                .filter(session -> session.getClosedTime() == null)
                .toList();
        if (activeSessions.size() >= 1) {
            log.info("Active session:" + activeSessions.get(0));
            return activeSessions.get(0);
        }
        return null;
    }
}
