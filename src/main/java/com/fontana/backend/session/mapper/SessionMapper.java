package com.fontana.backend.session.mapper;

import com.fontana.backend.session.dto.SessionRequestDTO;
import com.fontana.backend.session.dto.SessionResponseDTO;
import com.fontana.backend.session.dto.SessionWatcherRequestDTO;
import com.fontana.backend.session.entity.Session;
import com.fontana.backend.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SessionMapper {

    @Value("${session.expiration-delay}")
    private String expirationDelay;

    private final AuthUtils appUtils;

    public Session map(SessionRequestDTO sessionRequestDTO) {
        String currentPrincipalName = appUtils.getAuthentication().getPrincipal().toString();

        return Session.builder()
                .username(currentPrincipalName)
                .openedTime(sessionRequestDTO.getOpenedTime())
                .closedTime(null)
                .expirationTime(sessionRequestDTO.getOpenedTime().plusMinutes(Integer.parseInt(expirationDelay)))
                .build();
    }

    public SessionResponseDTO map(Session session) {
        int logsAmount = session.getLogs().size();

        return SessionResponseDTO.builder()
                .id(session.getId())
                .username(session.getUsername())
                .openedTime(session.getOpenedTime())
                .closedTime(session.getClosedTime())
                .expirationTime(session.getExpirationTime())
                .isForcedToClose(session.isForcedToClose())
                .isAutoClosed(session.isAutoClosed())
                .logsAmount(logsAmount)
                .watchers(session.getWatchers())
                .build();
    }
}
