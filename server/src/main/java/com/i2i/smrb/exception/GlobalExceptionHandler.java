package com.i2i.smrb.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.i2i.smrb.dto.ResponseMessage;

/**
 * <p>
 * Global exception handler for managing and handling exceptions across the application.
 * Provides centralized handling of various types of exceptions, including validation errors and format issues.
 * </p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ResponseMessage> handleLoginException(LoginException e) {
        ResponseMessage response = new ResponseMessage(e.getMessage());
        logger.error("Error : {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}