package com.fontana.backend.exception.customExceptions;

public class RoleNotAllowedException extends RuntimeException {

    public RoleNotAllowedException(String message) {
        super(message);
    }

    public RoleNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleNotAllowedException(Throwable cause) {
        super(cause);
    }
}
