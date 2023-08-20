package com.fontana.backend.exception;

import com.fontana.backend.exception.customExceptions.NotFoundException;
import com.fontana.backend.exception.customExceptions.SessionNotModifiedException;
import com.fontana.backend.security.jwt.JwtExpiredOrUntrustedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionController {

    /**
     * This method is responsible for handling validation errors that occur when processing request parameters
     * in the controller
     *
     * @param exception MethodArgumentNotValidException object representing the validation errors that occurred
     *                  during request processing.
     * @return ResponseEntity containing a map of field names and their corresponding error messages.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleBindErrors(MethodArgumentNotValidException exception) {
        Map<String, String> errorList = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorList.put(fieldName, errorMessage);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorList);
    }

    private Map<String, Object> generateDefaultExcResponseBody(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("timestamp", System.currentTimeMillis());
        log.error(message);
        return response;
    }

    @ExceptionHandler(JwtExpiredOrUntrustedException.class)
    public ResponseEntity<Map<String, Object>> handleJwtException(JwtExpiredOrUntrustedException exc) {
        Map<String, Object> response = generateDefaultExcResponseBody(exc.getMessage());
        log.info("JWT Expired or untrusted exception invoked in exception handler.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFoundException(NotFoundException exc) {
        Map<String, Object> response = generateDefaultExcResponseBody(exc.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(SessionNotModifiedException.class)
    public ResponseEntity<Map<String, Object>> handleSessionNotModifiedException(SessionNotModifiedException exc) {
        Map<String, Object> response = generateDefaultExcResponseBody(exc.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadableException(HttpMessageNotReadableException exc) {
        Map<String, Object> response = generateDefaultExcResponseBody(exc.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException exc) {
        Map<String, Object> response = generateDefaultExcResponseBody(exc.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}

