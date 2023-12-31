package com.fontana.backend.security.auth;

import com.fontana.backend.security.LdapService;
import com.fontana.backend.security.TokenType;
import com.fontana.backend.security.blacklist.entity.BlacklistedToken;
import com.fontana.backend.security.blacklist.repository.BlacklistedTokenRepository;
import com.fontana.backend.security.jwt.JwtService;
import com.fontana.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final JwtService jwtService;
    private final LdapService ldapService;
    private final BlacklistedTokenRepository blacklistedTokenRepository;
    private final UserRepository userRepository;

    @Value("${jwt.token-type}")
    private String tokenType;

//    @Value("${jwt.access-expiration-delay}")
    @Value("999999999999")
    private String accessExpDelay;

    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {
        if (!ldapService.isLdapRegistered(request.getUsername(), request.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String jwtAccessToken = jwtService.generateAccessToken(request.getUsername());
        String jwtRefreshToken = jwtService.generateRefreshToken(request.getUsername());

        String role = userRepository.findByUsername(request.getUsername()).get().getRole().getName();

        return ResponseEntity.ok(generateAuthResponse(jwtAccessToken, jwtRefreshToken, role));
    }

    public ResponseEntity<?> refreshToken(String oldRefreshToken) {
        String username = jwtService.extractUsername(oldRefreshToken);
        String newJwtAccessToken = jwtService.generateAccessToken(username);

        jwtService.blacklistToken(oldRefreshToken, TokenType.REFRESH);
        return ResponseEntity.ok(generateAuthResponse(newJwtAccessToken, oldRefreshToken));
    }

    private AuthenticationResponse generateAuthResponse(String accessToken, String refreshToken, String role) {
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType(tokenType)
                .role(role)
                .expiration(LocalDateTime.now().plus(Long.parseLong(accessExpDelay), ChronoUnit.MILLIS))
                .build();
    }

    private AuthenticationResponse generateAuthResponse(String accessToken, String refreshToken) {
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType(tokenType)
                .expiration(LocalDateTime.now().plus(Long.parseLong(accessExpDelay), ChronoUnit.MILLIS))
                .build();
    }

    public void blacklistToken(String token, TokenType tokenType) {
        Date now = new Date();
        Date expirationDate = Date.from(now.toInstant().plus(Duration.ofHours(2)));
        if (tokenType == null) {
            throw new IllegalArgumentException("TokenType must not be null");
        }
        BlacklistedToken blacklistedToken = BlacklistedToken.builder()
                .token(token)
                .tokenType(tokenType)
                .dateAdded(now)
                .expirationDate(expirationDate)
                .build();

        blacklistedTokenRepository.save(blacklistedToken);
    }
}