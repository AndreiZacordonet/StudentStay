package dev.studentstay.Documente.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static ResponseEntity<?> bodyBuild(HttpStatus status, String error, String message, HttpServletRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", message);
        body.put("path", request.getQueryString() == null ? request.getRequestURI() : request.getRequestURI() + "?" +
                request.getQueryString());

        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<?> handleHttpClientErrorException(HttpClientErrorException ex, HttpServletRequest request) {
        return bodyBuild(HttpStatus.valueOf(ex.getStatusCode().value()), ex.getStatusText(), ex.getMessage(), request);
    }
}
