package com.example.SpringEcommerceBackend.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.SpringEcommerceBackend.dto.AuthResponse;
import com.example.SpringEcommerceBackend.dto.LoginRequest;
import com.example.SpringEcommerceBackend.dto.RegisterRequest;
import com.example.SpringEcommerceBackend.dto.UserResponse;
import com.example.SpringEcommerceBackend.entity.User;
import com.example.SpringEcommerceBackend.enums.Role;
import com.example.SpringEcommerceBackend.exception.DuplicateEmailException;
import com.example.SpringEcommerceBackend.exception.ResourceNotFoundException;
import com.example.SpringEcommerceBackend.mapper.UserMapper;
import com.example.SpringEcommerceBackend.repository.UserRepository;
import com.example.SpringEcommerceBackend.secuirty.JwtService;
import com.example.SpringEcommerceBackend.service.UserService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    // we can also use di to take userrepository in the constructor, but here we are using @RequiredArgsConstructor annotation from lombok to generate a constructor with required arguments (final fields) for us.
    // public UserServiceImpl(UserRepository userRepository) {
    //     this.userRepository = userRepository;
    // }

    @Override
    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("Email already registered");
        }

        User user = UserMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRole(Role.ADMIN); // for testing 

        User savedUser = userRepository.save(user);

        return UserMapper.toResponse(savedUser);
    }

    @Override
public AuthResponse login(LoginRequest request) {

    User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
        throw new RuntimeException("Invalid credentials");
    }

    String token = jwtService.generateToken(user.getEmail());

    return AuthResponse.builder()
            .token(token)
            .build();
}

   
}
