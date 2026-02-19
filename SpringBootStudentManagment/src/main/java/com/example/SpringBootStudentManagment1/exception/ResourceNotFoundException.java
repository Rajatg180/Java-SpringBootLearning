package com.example.SpringBootStudentManagment1.exception;


// adding custom exceptions for better error handling and clearer API responses
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
