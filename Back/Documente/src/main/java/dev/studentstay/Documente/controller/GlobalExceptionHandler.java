package dev.studentstay.Documente.controller;

import dev.studentstay.Documente.exceptions.*;
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

    @ExceptionHandler(DocumentNotFoundException.class)
    public ResponseEntity<?> handleDocumentNotFoundException(DocumentNotFoundException ex, HttpServletRequest request) {
        return bodyBuild(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), request);
    }

    @ExceptionHandler(DocumentFaraCerereException.class)
    public ResponseEntity<?> handleDocumentFaraCerereException(DocumentFaraCerereException ex, HttpServletRequest request) {
        return bodyBuild(HttpStatus.NOT_FOUND, "Cerere inexistenta", ex.getMessage(), request);
    }

    @ExceptionHandler(TextExtractionException.class)
    public ResponseEntity<?> handleTextExtractionException(TextExtractionException ex, HttpServletRequest request) {
        return bodyBuild(HttpStatus.REQUEST_TIMEOUT, "Ocr error", ex.getMessage(), request);
    }

    @ExceptionHandler(ClasamentEntryNotFoundExeception.class)
    public ResponseEntity<?> handleClasamentEntryNotFoundExeception(ClasamentEntryNotFoundExeception ex, HttpServletRequest request) {
        return bodyBuild(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), request);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<?> handleEmailNotFoundException(EmailNotFoundException ex, HttpServletRequest request) {
        return bodyBuild(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), request);
    }

    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<?> handleRoomNotFoundException(RoomNotFoundException ex, HttpServletRequest request) {
        return bodyBuild(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), request);
    }

    @ExceptionHandler(RepartizareNodFoundException.class)
    public ResponseEntity<?> handleRepartizareNodFoundException(RepartizareNodFoundException ex, HttpServletRequest request) {
        return bodyBuild(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), request);
    }
}
