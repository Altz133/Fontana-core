package com.fontana.backend.security.auth;

import com.fontana.backend.security.blacklist.BlacklistTokenRequest;
import com.fontana.backend.security.blacklist.TokenCleanupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.fontana.backend.config.RestEndpoints.*;

@RestController
@RequestMapping(AUTH)
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;
    private final TokenCleanupService tokenCleanupService;

    @PostMapping(AUTH_AUTHENTICATE)
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest request) {
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
