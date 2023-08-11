package com.fontana.backend.session;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SessionService {

    List<Session> findAll();

    Session findById(Integer id);

    ResponseEntity<?> add(SessionDTO sessionDTO);

    ResponseEntity<?> updateCloseSession(SessionCloseRequest sessionCloseRequest);

    ResponseEntity<?> checkIsActive(String username);
}
