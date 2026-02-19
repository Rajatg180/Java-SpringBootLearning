package com.example.SpringBootUserManagement.security;

import com.example.SpringBootUserManagement.entity.User;
import com.example.SpringBootUserManagement.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// This service tells Spring Security HOW to load a user from DB.
// When user tries to login, Spring Security will call loadUserByUsername() method of this service to get user details (like email, password, roles) from DB and perform authentication.
// now the user detailes are stored in UserPrincipal class which implements UserDetails interface. This is a common pattern in Spring Security to create a custom UserDetails implementation that wraps your own User entity. This allows you to keep your domain model separate from the security model while still providing the necessary information for authentication and authorization.
// UserPriniciple will do authentication and authorization for us. It will provide user details to spring security and spring security will use that details to perform authentication and authorization.
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Spring Security calls this method during authentication (login).
    // It passes "username" - we use email here.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // Fetch user from DB
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Convert our User entity -> Spring Security UserDetails
        // this is kind of adapter pattern, we are adapting our User entity to Spring Security's UserDetails interface.
        return new UserPrincipal(user);
    }
}
