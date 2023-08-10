package com.fontana.backend.session;

import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface SessionService {

    List<Session> findAll();

    Session findById(Integer id);

    ResponseEntity<?> add(SessionDTO sessionDTO);

    ResponseEntity<?> updateCloseSession(Integer sessionId, LocalDateTime closedTime);
}
