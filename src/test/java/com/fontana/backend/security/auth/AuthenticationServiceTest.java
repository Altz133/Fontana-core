package com.fontana.backend.security.auth;

import com.fontana.backend.security.LdapService;
import com.fontana.backend.security.TokenType;
import com.fontana.backend.security.jwt.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private LdapService ldapService;

    @InjectMocks
    private AuthenticationService authenticationService;

    private String username;
    private String password;
    private String mockAccessToken;
    private String mockRefreshToken;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(authenticationService, "accessExpDelay", Integer.toString(900000));
        this.username = "fontanna_operator";
        this.password = "ooR6Iequ3hove1Ahkeez";
        this.mockAccessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmb250YW5uYV9vcGVyYXRvciIsImlhdCI6MTY5MjEyNTY5NSwiZXhwIjoxNjkyMTI2NTk1fQ.xEgHH6Flkxx8QVPTvvDmES1J4GASzuOqC_oFS0Bf-Pk";
        this.mockRefreshToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmb250YW5uYV9vcGVyYXRvciIsImlhdCI6MTY5MjEyNTY5NSwiZXhwIjoxNjkyMTMyODk1fQ.U3-Lp4I2CdXDVxJdcMhmsijQQ_Ui4uvuSB4OgBT_MpI";
    }

    @Test
    public void testAuthenticate_Successful() {
        AuthenticationRequest request = new AuthenticationRequest(username, password);

        when(ldapService.isLdapRegistered(request.getUsername(), request.getPassword())).thenReturn(true);
        when(jwtService.generateAccessToken(request.getUsername())).thenReturn(mockAccessToken);
        when(jwtService.generateRefreshToken(request.getUsername())).thenReturn(mockRefreshToken);

        ResponseEntity<AuthenticationResponse> responseEntity = authenticationService.authenticate(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockAccessToken, Objects.requireNonNull(responseEntity.getBody()).getAccessToken());
        assertEquals(mockRefreshToken, responseEntity.getBody().getRefreshToken());

        verify(jwtService, times(1)).generateAccessToken(request.getUsername());
        verify(jwtService, times(1)).generateRefreshToken(request.getUsername());
    }

    @Test
    public void testAuthenticate_Unauthorized() {
        AuthenticationRequest request = new AuthenticationRequest(username, password);
        when(ldapService.isLdapRegistered(request.getUsername(), request.getPassword())).thenReturn(false);

        ResponseEntity<AuthenticationResponse> responseEntity = authenticationService.authenticate(request);

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());

        verify(jwtService, never()).generateAccessToken(any());
        verify(jwtService, never()).generateRefreshToken(any());
    }

    @Test
    public void testRefreshToken_Successful() {
        String oldRefreshToken = mockRefreshToken;
        String newAccessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmb250YW5uYV9vcGVyYXRvciIsImlhdCI6MTY5MjEyOTg3NSwiZXhwIjoxNjkyMTMwNzc1fQ.zyktbvTpF7_52HSsKFZQkFbwa5JYEFSZ-vU-9eY0HKY";

        when(jwtService.extractUsername(oldRefreshToken)).thenReturn(username);
        when(jwtService.generateAccessToken(username)).thenReturn(newAccessToken);

        ResponseEntity<?> responseEntity = authenticationService.refreshToken(oldRefreshToken);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(newAccessToken, ((AuthenticationResponse) Objects.requireNonNull(responseEntity.getBody())).getAccessToken());

        verify(jwtService, times(1)).extractUsername(oldRefreshToken);
        verify(jwtService, times(1)).generateAccessToken(username);
        verify(jwtService, times(1)).blacklistToken(oldRefreshToken, TokenType.REFRESH);
    }

    @Test
    public void testBlacklistToken() {
        String refreshToken = mockRefreshToken;

        authenticationService.blacklistToken(refreshToken);

        verify(jwtService, times(1)).blacklistToken(refreshToken, TokenType.REFRESH);
    }
}