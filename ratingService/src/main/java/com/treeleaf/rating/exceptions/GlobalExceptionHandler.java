package com.treeleaf.rating.exceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Map<String, String>> handleAllException(Exception exception) {
        log.info("Handling exception ...");

        if(exception instanceof DataAccessException) {
            DataAccessException ex = (DataAccessException) exception;
            return handleServiceOrDataLayerException(ex, HttpStatus.BAD_REQUEST);
        }

        if(exception instanceof BadCredentialsException) {
            BadCredentialsException ex = (BadCredentialsException) exception;
            return handleServiceOrDataLayerException(ex, HttpStatus.UNAUTHORIZED);
        }

        if(exception instanceof UsernameNotFoundException) {
            UsernameNotFoundException ex = (UsernameNotFoundException) exception;
            return handleServiceOrDataLayerException(ex, HttpStatus.BAD_REQUEST);
        }

        if(exception instanceof AccessDeniedException) {
            AccessDeniedException ex = (AccessDeniedException) exception;
            return handleServiceOrDataLayerException(ex, HttpStatus.FORBIDDEN);
        }

        return handleInternalServerError(exception);
    }

    private ResponseEntity<Map<String, String>> handleServiceOrDataLayerException(Exception exception, HttpStatus status) {
        log.warn("{}, {}", exception.getClass(), exception.getMessage());

        Map<String, String> response = new LinkedHashMap<>();
        response.put("message", exception.getMessage());
        return ResponseEntity.status(status).body(response);
    }

    private ResponseEntity<Map<String, String>> handleInternalServerError(Exception exception) {
        log.error("Exception occurred", exception);

        Map<String, String> response = new LinkedHashMap<>();
        response.put("message", exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }


    private ResponseEntity<Map<String, String>> handleExceptionWithCustomMessage(Exception exception, String message, HttpStatus status) {
        log.warn("{}, {}", exception.getClass(), exception.getMessage());

        Map<String, String> response = new LinkedHashMap<>();
        response.put("message", message);
        return ResponseEntity.status(status).body(response);
    }
}

