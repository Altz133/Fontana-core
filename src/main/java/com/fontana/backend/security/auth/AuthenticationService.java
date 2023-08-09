package com.fontana.backend.security.auth;

import com.fontana.backend.security.LdapService;
import com.fontana.backend.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final LdapService ldapService;

    private String jwtAccessToken;

    @Value("${jwt.token-type}")
    private String tokenType;

    @Value("${jwt.access-expiration-delay}")
    private String accessExpDelay;

    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {
        if (!ldapService.isLdapRegistered(request.getUsername(), request.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        jwtAccessToken = jwtService.generateAccessToken(request.getUsername());
        String jwtRefreshToken = jwtService.generateRefreshToken(request.getUsername());

        return ResponseEntity.ok(generateAuthResponse(jwtAccessToken, jwtRefreshToken));
    }

    public ResponseEntity<?> refreshToken(String refreshToken) {
        String username = jwtService.extractUsername(refreshToken);
        jwtAccessToken = jwtService.generateAccessToken(username);

        // Blacklist the provided refresh token after use
        jwtService.blacklistToken(refreshToken);

        return ResponseEntity.ok(generateAuthResponse(jwtAccessToken, refreshToken));
    }

    private AuthenticationResponse generateAuthResponse(String accessToken, String refreshToken) {
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType(tokenType)
                .expiration(LocalDateTime.now().plus(Long.parseLong(accessExpDelay), ChronoUnit.MILLIS))
                .build();
    }

    public void blacklistToken(String refreshToken) {
        jwtService.blacklistToken(refreshToken);
    }
}
