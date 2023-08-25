package com.fontana.backend.session.service;

import com.fontana.backend.session.dto.SessionWatcherRequestDTO;
import org.springframework.http.ResponseEntity;

public interface NotificationService {

    ResponseEntity<?> updateSingleSessionWatcher(Integer sessionId, SessionWatcherRequestDTO request);

    ResponseEntity<?> updateAllSessionsWatchers(SessionWatcherRequestDTO request);

    ResponseEntity<?> findNonDisplayedAmount(String username);
}
