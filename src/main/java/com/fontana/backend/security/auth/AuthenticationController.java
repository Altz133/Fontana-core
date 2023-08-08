package com.fontana.backend.security.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
        return authService.refreshToken(token);
    }
}
