package com.fontana.backend.security.filters;

import com.fontana.backend.session.service.SessionService;
import com.fontana.backend.utils.AuthUtils;
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

    private final AuthUtils appUtils;
    private final SessionService sessionService;
    @Value("${session.custom-header}")
    private String sessionHeader;
    @Value("${session.header-prefix}")
    private String sessionPrefix;

    /**
     * Filters incoming requests if "X-Live-Control" header with value of "active" can be found. If a valid
     * session header is present, it updates session expiration time.
     *
     * @param request     incoming HttpServletRequest object containing the request information.
     * @param response    HttpServletResponse object for building and sending the response.
     * @param filterChain FilterChain object used for invoking the next filters in the chain.
     * @throws ServletException if any servlet-specific error occurs during the filter processing.
     * @throws IOException      if an I/O error occurs during the filter processing.
     */
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
        }

        filterChain.doFilter(request, response);
    }
}
