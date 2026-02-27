package com.grandstay.hotel.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import com.grandstay.hotel.util.wrappers.ErrorResponse;

import jakarta.persistence.NoResultException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ErrorResponse buildErrorResponse(String message, HttpStatus status, HttpServletRequest request, String errorClass) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage(message);
        response.setTimestamp(LocalDateTime.now().toString());
        response.setPath(request.getRequestURI());
        response.setStatus(status.toString());
        response.setError(errorClass);
        return response;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex, HttpServletRequest request) {
        ErrorResponse body = buildErrorResponse(
                ex.getMessage(), HttpStatus.NOT_FOUND, request, ex.getClass().getSimpleName());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(
            BadRequestException ex, HttpServletRequest request) {
        ErrorResponse body = buildErrorResponse(
                ex.getMessage(), HttpStatus.BAD_REQUEST, request, ex.getClass().getSimpleName());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(
            UnauthorizedException ex, HttpServletRequest request) {
        ErrorResponse body = buildErrorResponse(
                ex.getMessage(), HttpStatus.UNAUTHORIZED, request, ex.getClass().getSimpleName());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(
            ConflictException ex, HttpServletRequest request) {
        ErrorResponse body = buildErrorResponse(
                ex.getMessage(), HttpStatus.CONFLICT, request, ex.getClass().getSimpleName());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(
            IllegalArgumentException ex, HttpServletRequest request) {
        ErrorResponse body = buildErrorResponse(
                ex.getMessage(), HttpStatus.BAD_REQUEST, request, ex.getClass().getSimpleName());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<ErrorResponse> handleNoResult(
            NoResultException ex, HttpServletRequest request) {
        ErrorResponse body = buildErrorResponse(
                "Requested resource not found", HttpStatus.NOT_FOUND, request, ex.getClass().getSimpleName());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, HttpServletRequest request) {
        ErrorResponse body = buildErrorResponse(
                ex.getMessage() != null ? ex.getMessage() : "Internal server error",
                HttpStatus.INTERNAL_SERVER_ERROR, request, ex.getClass().getName());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
