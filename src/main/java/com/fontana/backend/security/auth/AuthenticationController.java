package com.fontana.backend.security.auth;

import com.fontana.backend.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.fontana.backend.security.blacklist.TokenCleanupService;
import com.fontana.backend.security.blacklist.BlacklistTokenRequest;
import java.util.Map;

import static com.fontana.backend.config.RestEndpoints.*;

@RestController
@RequestMapping(AUTH)
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authService;
    private final JwtService jwtService;
    private final TokenCleanupService tokenCleanupService;

    @PostMapping(AUTH_AUTHENTICATE)
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest request) {
        log.info("Authenticating user: {}", request.getUsername());
        return authService.authenticate(request);
    }

    @PostMapping(AUTH_REFRESHTOKEN)
    public ResponseEntity<?> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return authService.refreshToken(token);
    }

    @PostMapping(LOGOUT)
    public ResponseEntity<Void> logout(@RequestBody String refreshToken) {
        authService.blacklistToken(refreshToken);
        tokenCleanupService.removeFromBlacklistImmediately(refreshToken);
        return ResponseEntity.ok().build();
    }
    @PostMapping(REMOVE_TOKEN_IMMEDIATELY)
    public ResponseEntity<Void> removeTokenImmediately(@RequestBody String token) {
        tokenCleanupService.removeFromBlacklistImmediately(token);
        return ResponseEntity.ok().build();
    }


    @PostMapping(BLACKLIST)
    public ResponseEntity<Void> addToBlacklist(@Valid @RequestBody BlacklistTokenRequest tokenRequest) {
        String token = tokenRequest.getToken();
        authService.blacklistToken(token);
        return ResponseEntity.ok().build();
    }
}
