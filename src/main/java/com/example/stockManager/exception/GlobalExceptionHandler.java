package com.example.stockManager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.HashMap;

// The GlobalExceptionHandler class is responsible for handling exceptions that occur in the application and providing appropriate HTTP responses. It uses @RestControllerAdvice to apply globally to all controllers.
@RestControllerAdvice
public class GlobalExceptionHandler {
    // The handleNotFound method is responsible for handling ProductNotFoundException and returning a structured response with the appropriate HTTP status code and error message.
    @ExceptionHandler(ProductNotFoundException.class)

    // The handleNotFound method is responsible for handling ProductNotFoundException and returning a structured response with the appropriate HTTP status code and error message.
    public ResponseEntity<Map<String, Object>> handleNotFound(ProductNotFoundException ex){
        Map<String, Object> body = new HashMap<>(); // Create a map to hold the response body
        body.put("status", HttpStatus.NOT_FOUND.value()); // Set the HTTP status code to 404 (NOT_FOUND)
        body.put("message", ex.getMessage()); // Set the error message from the exception
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND); // Return a ResponseEntity with the response body and the NOT_FOUND status
    }

    // The handleValidationErrors method is responsible for handling MethodArgumentNotValidException, which occurs when validation of request parameters fails. It returns a structured response with the appropriate HTTP status code and validation error details.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    // This method handles validation errors and returns a structured response with the appropriate HTTP status code and error details.

    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex){
        Map<String, Object> body = new HashMap<>(); // Create a map to hold the response body
        body.put("status", HttpStatus.BAD_REQUEST.value()); // Set the HTTP status code to 400 (BAD_REQUEST)
        body.put("message", "Validasi gagal"); // Set a generic error message indicating validation failure
        body.put("errors", ex.getBindingResult().getFieldErrors().stream() // Map the field errors to a list of error details
                .map(error -> Map.of("field", error.getField(), "message", error.getDefaultMessage()))
                .toList());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST); // Return a ResponseEntity with the response body and the BAD_REQUEST status
    }

    // The handleSupplierNotFound method is responsible for handling SupplierNotFoundException and returning a structured response with the appropriate HTTP status code and error message.
    @ExceptionHandler(SupplierNotFoundException.class)

    // This method handles SupplierNotFoundException and returns a structured response with the appropriate HTTP status
    public ResponseEntity<Map<String, Object>> handleSupplierNotFound(SupplierNotFoundException ex){
        Map<String, Object> body = new HashMap<>(); // Create a map to hold the response body
        body.put("status", HttpStatus.NOT_FOUND.value()); // Set the HTTP status code to 404 (NOT_FOUND)
        body.put("message", ex.getMessage()); // Set the error message from the exception
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND); // Return a ResponseEntity with the response body and the NOT_FOUND status
    }
}
