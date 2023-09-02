package com.fontana.backend.session.service;

import com.fontana.backend.session.dto.SessionCloseRequest;
import com.fontana.backend.session.dto.SessionRequestDTO;
import com.fontana.backend.session.dto.SessionResponseDTO;
import com.fontana.backend.session.entity.Session;
import com.fontana.backend.user.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SessionService {

    List<SessionResponseDTO> findAll(String watcher, Integer size);

    SessionResponseDTO findById(Integer id);

    ResponseEntity<?> add(SessionRequestDTO sessionRequestDTO);

    ResponseEntity<?> updateCloseSession(SessionCloseRequest sessionCloseRequest);

    boolean checkIsActive(String username);

    void updateExpirationTime();

    List<Session> filterSessionsInReversedOrder(User user);

    Session getActiveSession();
}
