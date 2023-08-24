package com.fontana.backend.exception.customExceptions;

public class DeviceDTOFactoryException extends IllegalArgumentException {

    public DeviceDTOFactoryException(String message) {
        super(message);
    }

    public DeviceDTOFactoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeviceDTOFactoryException(Throwable cause) {
        super(cause);
    }
}
