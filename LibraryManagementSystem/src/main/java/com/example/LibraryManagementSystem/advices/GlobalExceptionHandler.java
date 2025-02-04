package com.example.LibraryManagementSystem.advices;

import com.example.LibraryManagementSystem.custExceptions.ResourceNotFoundException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex)
    {
        return new ResponseEntity<>(ex.getLocalizedMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex)
    {
        return new ResponseEntity<>(ex.getLocalizedMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
