package com.fontana.backend.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SessionMapper {

    public Session map(SessionDTO sessionDTO) {
        Session session = Session.builder()
                .openedTime(sessionDTO.getOpenedTime())
                .closedTime(null)
                .build();

        log.debug(String.valueOf(session));
        return session;
    }
}
