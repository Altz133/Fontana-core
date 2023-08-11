package com.fontana.backend.exception.customExceptions;

public class SessionNotModifiedException extends RuntimeException {

    public SessionNotModifiedException(String message) {
        super(message);
    }

    public SessionNotModifiedException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessionNotModifiedException(Throwable cause) {
        super(cause);
    }
}
