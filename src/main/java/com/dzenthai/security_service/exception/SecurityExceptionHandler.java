package com.dzenthai.security_service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;


@Slf4j
@RestControllerAdvice
public class SecurityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleUnknownException(RuntimeException ex) {
        var status = HttpStatus.UNAUTHORIZED;
        var timestamp = LocalDateTime.now();
        var data = ExceptionData.builder()
                .status(status.value())
                .message(ex.getMessage())
                .timestamp(timestamp)
                .build();
        log.error("SecurityExceptionHandler | Unknown exception, status: {}, timestamp: {}, exception: {}",
                status, timestamp, ex.getMessage());
        return new ResponseEntity<>(data, status);
    }

    @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class})
    public ResponseEntity<?> handleAuthExceptions(RuntimeException ex) {
        var status = HttpStatus.BAD_REQUEST;
        var timestamp = LocalDateTime.now();
        var data = ExceptionData.builder()
                .status(status.value())
                .message(ex.getMessage())
                .timestamp(timestamp)
                .build();
        log.error("SecurityExceptionHandler | Authentication exception, status: {}, timestamp: {}, exception: {}",
                status, timestamp, ex.getMessage());
        return new ResponseEntity<>(data, status);
    }
}
