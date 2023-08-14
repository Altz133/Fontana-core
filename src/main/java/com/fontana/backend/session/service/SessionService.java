package com.fontana.backend.session.service;

import com.fontana.backend.session.dto.SessionCloseRequest;
import com.fontana.backend.session.dto.SessionDTO;
import com.fontana.backend.session.entity.Session;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SessionService {

    List<Session> findAll();

    Session findById(Integer id);

    ResponseEntity<?> add(SessionDTO sessionDTO);

    ResponseEntity<?> updateCloseSession(SessionCloseRequest sessionCloseRequest);

    boolean checkIsActive(String username);

    void updateExpirationTime();
}
