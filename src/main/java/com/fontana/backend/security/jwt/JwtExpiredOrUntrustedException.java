package com.fontana.backend.security.jwt;

public class JwtExpiredOrUntrustedException extends RuntimeException {

    public JwtExpiredOrUntrustedException(String message) {
        super(message);
    }

    public JwtExpiredOrUntrustedException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtExpiredOrUntrustedException(Throwable cause) {
        super(cause);
    }
}
