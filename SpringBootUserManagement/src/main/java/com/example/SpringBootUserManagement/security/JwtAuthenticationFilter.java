package com.example.SpringBootUserManagement.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// This filter runs ONCE per request
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            CustomUserDetailsService userDetailsService
    ) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // Read Authorization header
        String authHeader = request.getHeader("Authorization");

        // If header missing or doesn't start with Bearer → skip
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract token from "Bearer <token>"
        String token = authHeader.substring(7);

        // Validate token
        if (jwtService.isTokenValid(token)) {

            // Extract email from token
            String email = jwtService.extractUsername(token);

            // Load user from DB
            UserPrincipal userPrincipal =
                    (UserPrincipal) userDetailsService.loadUserByUsername(email);

            // Create Authentication object
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userPrincipal,
                            null,
                            userPrincipal.getAuthorities()
                    );

            authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            // VERY IMPORTANT:
            // Set authentication in SecurityContext
            // This is how Spring Security knows that the user is authenticated for this request.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Continue request chain
        // means that after this filter is done, the request will continue to the next filter in the chain, and eventually to the controller if authentication is successful.
        filterChain.doFilter(request, response);
    }
}
