package com.fontana.backend.session;

import com.fontana.backend.exception.customExceptions.NotFoundException;
import com.fontana.backend.exception.customExceptions.SessionNotModifiedException;
import com.fontana.backend.role.RoleType;
import com.fontana.backend.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fontana.backend.config.RestEndpoints.SESSION;

@Service
@RequiredArgsConstructor
@Slf4j
public class SessionServiceImpl implements SessionService {

    @Value("${session.not-found-msg}")
    private String notFoundMsg;

    @Value("${session.busy-msg}")
    private String sessionBusyMsg;

    @Value("${session.not-allowed-to-close-msg}")
    private String notAllowedToCloseMsg;

    @Value("${session.already-closed}")
    private String sessionAlreadyClosedMsg;

    @Value("${session.expiration-delay}")
    private String expirationDelay;

    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;
    private final AppUtils appUtils;

    @Scheduled(fixedRate = 30000)
    public void autoCloseSession() {
        Session session = getActiveSession();

        if (session != null && session.getExpirationTime().isBefore(LocalDateTime.now())) {
            Session updated = buildUpdatedSession(session, null, false, true);
            sessionRepository.save(updated);
            log.info("Auto closed session due to no activity: " + session);
        }
    }

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
        String authority = appUtils.extractAuthenticatedAuthority();

        if (authority.equals(RoleType.VIEWER.name())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (activeSession != null && authority.equals(RoleType.ADMIN.name())) {
            Session updated = buildUpdatedSession(activeSession, null, true, false);
            log.info("Session force-closed by admin: " + updated);
            sessionRepository.save(updated);
        }

        if (activeSession != null) {
            SessionBusyResponse response = buildSessionBusyResponse(sessionBusyMsg, activeSession);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        Session saved = sessionRepository.save(sessionMapper.map(sessionDTO));
        log.info(saved.toString());

        return buildAddSessionResponse(saved);
    }

    @Override
    public ResponseEntity<?> updateCloseSession(SessionCloseRequest sessionCloseRequest) {
        Session activeSession = getActiveSession();
        String authority = appUtils.extractAuthenticatedAuthority();
        log.info("Request:" + sessionCloseRequest);

        if (activeSession == null) {
            throw new SessionNotModifiedException(sessionAlreadyClosedMsg);
        }

        String currentPrincipalName = appUtils.getAuthentication().getPrincipal().toString();

        if (activeSession.getUsername().equals(currentPrincipalName) || authority.equals(RoleType.ADMIN.name())) {
            Session updated = buildUpdatedSession(
                    activeSession, sessionCloseRequest, false, false);
            log.info("Updated:" + updated);

            sessionRepository.save(updated);
            return ResponseEntity.ok().build();
        } else {
            throw new SessionNotModifiedException(notAllowedToCloseMsg);
        }
    }

    @Override
    public boolean checkIsActive(String username) {
        Session session = getActiveSession();
        log.info(String.valueOf(session));

        if (session == null) {
            throw new NotFoundException(notFoundMsg);
        }

        return session.getClosedTime() == null;
    }

    public void updateExpirationTime() {
        Session session = getActiveSession();

        if (session != null) {
            session.setExpirationTime(LocalDateTime.now().plusMinutes(Integer.parseInt(expirationDelay)));
            sessionRepository.save(session);
        }
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

    private ResponseEntity<?> buildAddSessionResponse(Session saved) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, SESSION.concat("/").concat(saved.getId().toString()));

        Map<String, LocalDateTime> response = new HashMap<>();
        response.put("expirationTime", saved.getExpirationTime());

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(response);
    }

    private Session buildUpdatedSession(
            Session activeSession,
            SessionCloseRequest request,
            boolean isForcedToClose,
            boolean isAutoClosed) {
        return Session.builder()
                .id(activeSession.getId())
                .username(activeSession.getUsername())
                .openedTime(activeSession.getOpenedTime())
                .closedTime(request != null ? request.getClosedTime() : LocalDateTime.now())
                .expirationTime(request != null ? request.getClosedTime() : LocalDateTime.now())
                .isForcedToClose(isForcedToClose)
                .isAutoClosed(isAutoClosed)
                .build();
    }

    private SessionBusyResponse buildSessionBusyResponse(String message, Session activeSession) {
        return SessionBusyResponse.builder()
                .message(message)
                .activeUserName(activeSession.getUsername())
                .activeSessionStartTime(activeSession.getOpenedTime())
                .intercepted(LocalDateTime.now())
                .build();
    }
}
