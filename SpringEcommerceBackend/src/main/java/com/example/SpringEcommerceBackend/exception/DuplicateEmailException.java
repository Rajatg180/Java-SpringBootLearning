package com.example.SpringEcommerceBackend.exception;

public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException(String message){
        super(message);
    }
    
}
