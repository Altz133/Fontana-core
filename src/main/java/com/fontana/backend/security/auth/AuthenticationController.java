package com.fontana.backend.security.auth;

import com.fontana.backend.security.TokenType;
import com.fontana.backend.security.blacklist.BlacklistTokenRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;


import static com.fontana.backend.config.RestEndpoints.*;

@RestController
@RequestMapping(AUTH)
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping(AUTH_AUTHENTICATE)
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest request) {
        return authService.authenticate(request);
    }

    @GetMapping(AUTH_REFRESHTOKEN)
    public ResponseEntity<?> refreshToken(@RequestHeader("${jwt.refresh-token-custom-header}") String token) {
        return authService.refreshToken(token);
    }

    @PostMapping(LOGOUT)
    public ResponseEntity<Void> logout(@RequestBody String refreshToken) {
        authService.blacklistToken(refreshToken, TokenType.REFRESH);
        return ResponseEntity.ok().build();
    }

    @PostMapping(BLACKLIST)
    public ResponseEntity<Void> addToBlacklist(@Valid @RequestBody BlacklistTokenRequest tokenRequest) {
        String token = tokenRequest.getToken();
        TokenType tokenType = tokenRequest.getTokenType();
        authService.blacklistToken(token, tokenType);
        return ResponseEntity.ok().build();
    }
}
