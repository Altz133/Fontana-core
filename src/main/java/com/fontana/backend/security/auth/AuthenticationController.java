package com.fontana.backend.security.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fontana/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return authService.authenticate(request);
    }

    /**
     * @param token has to contain prefix of "Bearer " in order to validate token properly
     * @return new access token wit updated expiration time
     */
    @PostMapping("/refreshtoken")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return authService.refreshToken(token);
    }
}
