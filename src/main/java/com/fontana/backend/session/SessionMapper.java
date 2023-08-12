package com.fontana.backend.session;

import com.fontana.backend.utils.AppUtils;
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

    private final AppUtils appUtils;

    public Session map(SessionDTO sessionDTO) {
        String currentPrincipalName = appUtils.getAuthentication().getPrincipal().toString();

        Session session = Session.builder()
                .username(currentPrincipalName)
                .openedTime(sessionDTO.getOpenedTime())
                .closedTime(null)
                .expirationTime(sessionDTO.getOpenedTime().plusMinutes(Integer.parseInt(expirationDelay)))
                .build();

        log.info("Mapped session: " + session);
        return session;
    }
}
