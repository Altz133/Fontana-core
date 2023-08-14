package com.fontana.backend.security.filters;

import com.fontana.backend.security.jwt.JwtService;
import com.fontana.backend.user.User;
import com.fontana.backend.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.prefix}")
    private String tokenPrefix;

    private final JwtService jwtService;
    private final UserRepository userRepository;

    /**
     * Performs the authentication filter logic for processing incoming HTTP requests.
     * This method is called by the framework when a request is received and is responsible
     * for inspecting the HTTP Authorization header, extracting the JSON Web Token (JWT),
     * and validating it using the provided JWT service.
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
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String jwt;
        final String username;

        if (authHeader == null || !authHeader.startsWith(tokenPrefix)) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(tokenPrefix.length());
        username = jwtService.extractUsername(jwt);
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        String authority = user.get().getRole().getName();

        if (SecurityContextHolder.getContext().getAuthentication() == null && jwtService.isTokenValid(jwt)) {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    username, null, Collections.singleton(new SimpleGrantedAuthority(authority))
            );

            log.info("Jwt filter: " + authToken);
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }
}