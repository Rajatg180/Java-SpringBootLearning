package com.example.SpringEcommerceBackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


// this annotation is used to generate getters, setters, toString, equals and hashCode methods for the class form project lombok
@Data
public class RegisterRequest{
    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;
}