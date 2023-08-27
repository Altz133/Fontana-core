package com.fontana.backend.session.service;

import com.fontana.backend.exception.customExceptions.NotFoundException;
import com.fontana.backend.exception.customExceptions.RoleNotAllowedException;
import com.fontana.backend.exception.customExceptions.SessionNotModifiedException;
import com.fontana.backend.role.entity.RoleType;
import com.fontana.backend.session.dto.*;
import com.fontana.backend.session.entity.Session;
import com.fontana.backend.session.mapper.SessionMapper;
import com.fontana.backend.session.repository.SessionRepository;
import com.fontana.backend.user.entity.User;
import com.fontana.backend.user.repository.UserRepository;
import com.fontana.backend.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    @Value("${session.role-not-allowed-msg}")
    private String roleNotAllowedMsg;

    @Value("${user.not-found-msg}")
    private String userNotFoundMsg;

    @Value("${cache.active-session}")
    private String activeSessionLabel;

    @Value("${session.expiration-delay}")
    private String expirationDelay;

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final SessionMapper sessionMapper;
    private final AuthUtils authUtils;
    private final CacheManager cacheManager;

    @Scheduled(fixedRate = 10000)
    public void autoCloseSession() {
        Session session = getActiveSession();

        if (session != null && session.getExpirationTime().isBefore(LocalDateTime.now())) {
            Session updated = buildUpdatedSession(session, null, false, true);
            sessionRepository.save(updated);
            Objects.requireNonNull(cacheManager.getCache(activeSessionLabel)).clear();
            log.info("(SESSION SCHEDULER) Auto closed session due to no activity: " + session);
            return;
        }

        log.info("(SESSION SCHEDULER) AutoCloseSession scheduler invoked with no action.");
    }

    /**
     * Retrieves sessions based on the provided watcher and optional size parameter, or all sessions if no arguments.
     *
     * @param watcher username of the watcher, or null if not filtering by watcher.
     * @param size maximum number of sessions to retrieve, or null for all sessions.
     * @return a list of SessionResponseDTO objects representing the retrieved sessions.
     * @throws NotFoundException if the user specified by 'watcher' is not found.
     * @throws RoleNotAllowedException if the user specified by 'watcher' does not have the required role.
     */
    @Override
    public List<SessionResponseDTO> findAll(String watcher, Integer size) {
        if (watcher != null) {
            User user = userRepository.findByUsername(watcher).orElseThrow(
                    () -> new NotFoundException(userNotFoundMsg));

            if (!user.getRole().getName().equals(RoleType.ADMIN.name())) {
                throw new RoleNotAllowedException(roleNotAllowedMsg);
            }

            log.info("Filtered sessions: " + filterSessionsInReversedOrder(user).size());
            return filterSessionsInReversedOrder(user).stream()
                    .map(sessionMapper::map)
                    .toList();
        }

        if (size != null) {
            Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "id"));

            return sessionRepository.findLatestSessions(pageable).stream()
                    .map(sessionMapper::map)
                    .toList();
        }

        return sessionRepository.findAll().stream()
                .map(sessionMapper::map)
                .toList();
    }

    @Override
    public SessionResponseDTO findById(Integer id) {
        Session session = sessionRepository.findById(id).orElseThrow(
                () -> new NotFoundException(notFoundMsg.concat(" " + id)));

        return sessionMapper.map(session);
    }

    @Override
    public ResponseEntity<?> add(SessionRequestDTO sessionRequestDTO) {
        Session activeSession = getActiveSession();
        String authority = authUtils.extractAuthenticatedAuthority();

        if (authority.equals(RoleType.VIEWER.name())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(buildRoleNotAllowedResponse());
        }

        if (activeSession != null && authority.equals(RoleType.ADMIN.name())) {
            Session updated = buildUpdatedSession(activeSession, null, true, false);
            log.info("Session force-closed by admin: " + updated);
            sessionRepository.save(updated);
        }

        if (activeSession != null && !authority.equals(RoleType.ADMIN.name())) {
            SessionBusyResponse response = buildSessionBusyResponse(sessionBusyMsg, activeSession);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        Session saved = sessionRepository.save(sessionMapper.map(sessionRequestDTO));
        Objects.requireNonNull(cacheManager.getCache(activeSessionLabel)).put("id", saved.getId());
        log.info("(OPEN) activeSession: " + saved);

        return buildAddSessionResponse(saved);
    }

    /**
     * Updates the state of the active session to indicate closure, based on the provided session close request.
     *
     * @param sessionCloseRequest containing session closed time.
     * @return ResponseEntity with no content if the session is successfully closed.
     * @throws SessionNotModifiedException if the active session is already closed or if the user is not authorized to close the session.
     */
    @Override
    public ResponseEntity<?> updateCloseSession(SessionCloseRequest sessionCloseRequest) {
        Session activeSession = getActiveSession();
        log.info("(CLOSE) active session: " + activeSession);
        String authority = authUtils.extractAuthenticatedAuthority();
        log.info("Request:" + sessionCloseRequest);

        if (activeSession == null) {
            throw new SessionNotModifiedException(sessionAlreadyClosedMsg);
        }

        String currentPrincipalName = authUtils.getAuthentication().getPrincipal().toString();

        if (activeSession.getUsername().equals(currentPrincipalName) || authority.equals(RoleType.ADMIN.name())) {
            Session updated = buildUpdatedSession(
                    activeSession, sessionCloseRequest, false, false);
            log.info("Updated:" + updated);

            sessionRepository.save(updated);
            Objects.requireNonNull(cacheManager.getCache(activeSessionLabel)).clear();

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

    /**
     * Retrieves the active session based on cached session ID.
     *
     * @return the active Session object if found, or null if no active session exists.
     */
    public Session getActiveSession() {
        Cache cache = cacheManager.getCache(activeSessionLabel);
        Cache.ValueWrapper valueWrapper = cache.get("id");

        if (valueWrapper != null && valueWrapper.get() != null) {
            log.info("Active session id: " + valueWrapper.get());
            return sessionRepository.findById((int) valueWrapper.get()).orElse(null);
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

    /**
     * Filters and retrieves a list of closed sessions in reversed order of opening time,
     * where the session's opening time is after the user's last role change.
     *
     * @param user for whom to filter sessions.
     * @return A list of SessionResponseDTO objects representing the filtered sessions.
     */
    public List<Session> filterSessionsInReversedOrder(User user) {
        Pageable pageable = PageRequest.of(0, 26, Sort.by(Sort.Direction.DESC, "id"));

        return sessionRepository.findAllInReversedOrderAfterDate(user.getLastRoleChange(), user.getUsername(), pageable).stream()
                .filter(session -> !session.getUsername().equals(authUtils.getAuthentication().getPrincipal()))
                .filter(session -> session.getClosedTime() != null)
                .filter(session -> session.getWatchers().stream()
                        .noneMatch(watcher -> watcher.getWatcher().equals(user.getUsername())))
                .toList();
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
                .logs(activeSession.getLogs())
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

    private SessionRoleNotAllowedResponse buildRoleNotAllowedResponse() {
        return SessionRoleNotAllowedResponse.builder()
                .message(roleNotAllowedMsg)
                .intercepted(LocalDateTime.now())
                .build();
    }
}
