package com.fontana.backend.session.service;

import com.fontana.backend.session.dto.SessionCloseRequest;
import com.fontana.backend.session.dto.SessionRequestDTO;
import com.fontana.backend.session.dto.SessionResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SessionService {

    List<SessionResponseDTO> findAll(String watcher);

    SessionResponseDTO findById(Integer id);

    ResponseEntity<?> add(SessionRequestDTO sessionRequestDTO);

    ResponseEntity<?> updateCloseSession(SessionCloseRequest sessionCloseRequest);

    boolean checkIsActive(String username);

    void updateExpirationTime();
}
