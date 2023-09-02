package com.fontana.backend.config;

import com.fontana.backend.session.entity.Session;
import com.fontana.backend.session.repository.SessionRepository;
import com.fontana.backend.session.service.SessionService;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class ShutdownBehaviorConfig {

    @Value("${cache.active-session}")
    private String activeSessionLabel;

    private final SessionService sessionService;
    private final SessionRepository sessionRepository;
    private final CacheManager cacheManager;

    @PreDestroy
    public void preDestroy() {
        Session activeSession = sessionService.getActiveSession();
        activeSession.setClosedTime(LocalDateTime.now());
        activeSession.setForcedToClose(true);

        sessionRepository.save(activeSession);
        Objects.requireNonNull(cacheManager.getCache(activeSessionLabel)).clear();
        log.info("Destroyed: " + activeSession);
    }
}
