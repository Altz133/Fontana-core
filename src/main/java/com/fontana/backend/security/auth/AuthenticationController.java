package com.fontana.backend.security.auth;

import com.fontana.backend.security.jwt.JwtExpiredOrUntrustedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static com.fontana.backend.config.RestEndpoints.*;

@RestController
@RequestMapping(AUTH)
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping(AUTH_AUTHENTICATE)
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Validated AuthenticationRequest request) {
        return authService.authenticate(request);
    }

    /**
     * @param token has to contain prefix of "Bearer " in order to validate token properly.
     * @return new access token wit updated expiration time
     */
    @PostMapping(AUTH_REFRESHTOKEN)
    public ResponseEntity<?> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            return authService.refreshToken(token);
        } catch(Exception exc) {
            throw new JwtExpiredOrUntrustedException(exc);
        }
    }

    @ExceptionHandler(JwtExpiredOrUntrustedException.class)
    public ResponseEntity<Map<String, Object>> handleJwtException(JwtExpiredOrUntrustedException exc) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", exc.getMessage());
        response.put("timestamp", Instant.now().toEpochMilli());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
