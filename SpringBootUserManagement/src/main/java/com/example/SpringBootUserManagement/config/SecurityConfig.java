package com.example.SpringBootUserManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.example.SpringBootUserManagement.security.CustomUserDetailsService;
import com.example.SpringBootUserManagement.security.JwtAuthenticationFilter;

// this class is used to configure spring security.
//  It will tell spring security how to authenticate and authorize users. 
// We will configure it to use our CustomUserDetailsService to load user details from DB and perform authentication and authorization based on that details. 
// We will also configure it to use JWT for authentication and authorization. We will also configure it to allow access to certain endpoints based on user roles (e.g., only ADMIN can access /admin endpoint).

@Configuration
@EnableMethodSecurity // this annotation is used to enable method level security. It allows us to use
                      // annotations like @PreAuthorize and @Secured on our controller methods to
                      // restrict access based on user roles.
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService,
            JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // Bcrypt password encoder bean is used to encode the password before saving it
    // to the database. It is also used to compare the raw password with the encoded
    // password during authentication.
    // @Bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {

        // This pulls the AuthenticationManager
        // that Spring Security already built internally
        return authenticationConfiguration.getAuthenticationManager();
    }

    // AuthenticationProvider is the component that knows:
    // - how to load user (UserDetailsService)
    // - how to verify password (PasswordEncoder)
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customUserDetailsService); // passing our
                                                                                                      // custom user
                                                                                                      // details service
                                                                                                      // to
                                                                                                      // authentication
                                                                                                      // provider
        provider.setPasswordEncoder(passwordEncoder()); // compare raw password vs hash
        return provider;
    }

    // This is the MAIN Spring Security configuration:
    // - which endpoints are public
    // - which are protected
    // - how sessions behave
    // SecurityFilterChain is used to configure the security filter chain, which is
    // a series of filters that are applied to incoming HTTP requests. It allows us
    // to define which endpoints are public and which are protected, as well as how
    // sessions behave (e.g., stateless for JWT).
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // For REST APIs, we usually disable CSRF because we are not using cookies-based
                // auth.
                .csrf(csrf -> csrf.disable())
                // Because we will use JWT, we don't want server-side sessions.
                // Every request will carry token, so session is stateless.
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Authorization rules:
                .authorizeHttpRequests((auth) -> auth
                        // Public endpoints
                        .requestMatchers("/auth/**").permitAll()

                        // Everything else requires authentication
                        .anyRequest().authenticated()

                )
                // Tell Spring which auth provider to use
                .authenticationProvider(authenticationProvider())
                // JWT filter must run BEFORE Spring's default auth filter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
