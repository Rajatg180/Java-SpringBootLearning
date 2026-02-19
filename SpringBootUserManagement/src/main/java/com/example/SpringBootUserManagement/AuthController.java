package com.example.SpringBootUserManagement;

import java.util.Set;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringBootUserManagement.constants.Role;
import com.example.SpringBootUserManagement.dto.AuthResponse;
import com.example.SpringBootUserManagement.dto.LoginRequest;
import com.example.SpringBootUserManagement.dto.RegisterRequest;
import com.example.SpringBootUserManagement.entity.User;
import com.example.SpringBootUserManagement.repository.UserRepository;
import com.example.SpringBootUserManagement.security.JwtService;
import com.example.SpringBootUserManagement.security.UserPrincipal;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    // this controller will handle user registration and login, 
    // and will use the UserRepository to interact with the database, 
    // PasswordEncoder to hash passwords, AuthenticationManager to authenticate users, 
    // and JwtService to generate JWT tokens.
    private final UserRepository userRepository; 
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

     // This endpoint returns the currently logged-in user
    @GetMapping("/me")
    public String me() {
        return "Hello";
    }

     // ================= REGISTER =================
    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterRequest request){
        // prevent duplicate users
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already in use");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        // Default role = USER
        user.setRoles(Set.of(Role.USER));

        userRepository.save(user);
        
        return "User registered successfully";
   }

   // ================= LOGIN =================
    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {

        // authenticate the user using the provided email and password. If authentication is successful, we will generate a JWT token for the user.
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

         // If authentication succeeds, generate JWT
        UserPrincipal userPrincipal =
                (UserPrincipal) new com.example.SpringBootUserManagement.security.CustomUserDetailsService(userRepository)
                        .loadUserByUsername(request.getEmail());

                        // generate jwt token
        String token = jwtService.generateToken(userPrincipal);

        return new AuthResponse(token);
    }


}
