package com.fontana.backend.exception.customExceptions;

public class DMXValidatorException extends RuntimeException{

        public DMXValidatorException(String message) {
            super(message);
        }

        public DMXValidatorException(String message, Throwable cause) {
            super(message, cause);
        }

        public DMXValidatorException(Throwable cause) {
            super(cause);
        }
}
