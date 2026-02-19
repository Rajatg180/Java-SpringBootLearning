package com.example.SpringBootStudentManagment1.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // This class will handle exceptions globally across the application
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class) // This method will handle ResourceNotFoundException
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "NOT_FOUND");
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body); 
    }   


    // this are predefined class , when we throw BadRequestException from our service layer, this handler will catch it and return a structured response with the error message and a BAD_REQUEST status code.
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(BadRequestException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "BAD_REQUEST");
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // this Class will handle validation errors and return a structured response with error details
    // MethodArgumentNotValidException is thrown when validation on an argument annotated with @Valid fails. This handler will extract the validation errors and return them in a structured format in the response body.
    // this inbuilt class is thrown when validation on an argument annotated with @Valid fails. This handler will extract the validation errors and return them in a structured format in the response body.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        Map<String, Object> body = new HashMap<>();
        body.put("error", "VALIDATION_ERROR");
        body.put("messages", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
