package com.dailycodework.gumiho_shops.exception;

import java.nio.file.AccessDeniedException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dailycodework.gumiho_shops.response.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    // @ExceptionHandler(ResourceNotFoundException.class)
    // public ResponseEntity<ApiResponse>
    // handleResourceNotFoundException(ResourceNotFoundException e) {
    // return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new
    // ApiResponse(e.getMessage(), null));
    // }

    // @ExceptionHandler(AlreadyExistingException.class)
    // public ResponseEntity<ApiResponse>
    // handleAlreadyExistingException(AlreadyExistingException e) {
    // return ResponseEntity.status(HttpStatus.CONFLICT).body(new
    // ApiResponse(e.getMessage(), null));
    // }

    // @ExceptionHandler(Exception.class)
    // public ResponseEntity<ApiResponse> handleGeneralException(Exception e) {
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new
    // ApiResponse("An error occurred", null));
    // }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        String message = "You do not have permission on this action!";
        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }
}
