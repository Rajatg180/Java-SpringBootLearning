package com.example.SpringEcommerceBackend.service;

import com.example.SpringEcommerceBackend.dto.AuthResponse;
import com.example.SpringEcommerceBackend.dto.LoginRequest;
import com.example.SpringEcommerceBackend.dto.RegisterRequest;
import com.example.SpringEcommerceBackend.dto.UserResponse;

public interface UserService {
    UserResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
