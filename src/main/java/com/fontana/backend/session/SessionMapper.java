package com.fontana.backend.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SessionMapper {

    public Session map(SessionDTO sessionDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getPrincipal().toString();

        Session session = Session.builder()
                .username(currentPrincipalName)
                .openedTime(sessionDTO.getOpenedTime())
                .closedTime(null)
                .build();

        log.info(session.toString());
        return session;
    }
}
