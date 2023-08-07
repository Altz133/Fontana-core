package com.fontana.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalErrorController {

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
}

