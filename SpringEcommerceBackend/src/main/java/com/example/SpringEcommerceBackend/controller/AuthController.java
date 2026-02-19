package com.example.SpringEcommerceBackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringEcommerceBackend.dto.AuthResponse;
import com.example.SpringEcommerceBackend.dto.LoginRequest;
import com.example.SpringEcommerceBackend.dto.RegisterRequest;
import com.example.SpringEcommerceBackend.dto.UserResponse;
import com.example.SpringEcommerceBackend.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }
    
    @PostMapping("/register")
    public UserResponse register( @Valid @RequestBody RegisterRequest request){
        return userService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return userService.login(request);
    }

}
