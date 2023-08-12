package com.fontana.backend.security.filters;

import com.fontana.backend.role.RoleType;
import com.fontana.backend.session.SessionService;
import com.fontana.backend.utils.AppUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class SessionStatusFilter extends OncePerRequestFilter {

    @Value("${session.custom-header}")
    private String sessionHeader;

    @Value("${session.header-prefix}")
    private String sessionPrefix;

    private final AppUtils appUtils;
    private final SessionService sessionService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String header = request.getHeader(sessionHeader);
        final String username;
        log.info("Session filter invoked" + header);

        if (header == null || !header.startsWith(sessionPrefix)) {
            filterChain.doFilter(request, response);
            return;
        }

        username = appUtils.getAuthentication().getPrincipal().toString();
        log.info("Logged user:" + username);

        if (sessionService.checkIsActive(username)) {
            sessionService.updateExpirationTime();
            filterChain.doFilter(request, response);
        }

        filterChain.doFilter(request,response);
    }
}
