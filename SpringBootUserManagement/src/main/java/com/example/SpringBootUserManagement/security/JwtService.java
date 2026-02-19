package com.example.SpringBootUserManagement.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

// this class is responsible for generating and validating JWT tokens. It will use the jjwt library to create and parse JWTs. It will also use the secret key defined in application.properties to sign the tokens. 
// This service will be used in the AuthController to generate tokens upon successful login and in the JwtAuthenticationFilter to validate tokens on incoming requests.

@Service
public class JwtService {
    private final SecretKey secretKey;
    private final long expirationMs;

    // this @Value annotation is used to inject the value of the secret key and
    // expiration time from application.properties file.
    // We have define these properties in application.properties as follows:
    public JwtService(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration-ms}") long expirationMs) {
        // Convert secret string to cryptographic key
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationMs = expirationMs;
    }

    // generate JWT token after successful login
    public String generateToken(UserPrincipal userPrincipal) {
        Date now = new Date();
        Date expDate = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .subject(userPrincipal.getUsername()) // email as subject
                .issuedAt(now)
                .expiration(expDate)
                // Add roles inside token (optional but useful)
                .claim(
                        "roles",
                        userPrincipal.getAuthorities().stream()
                                .map(a -> a.getAuthority())
                                .toList())

                .signWith(secretKey)
                .compact();
    }

    // extract email from jwt
    public String extractUsername(String token){
        return extractClaims(token).getSubject();
    }

    // get roles from token
    public String[] extractRoles(String token) {
        Claims claims = extractClaims(token);
        return claims.get("roles", String[].class);
    }

    // Validate token signature & expiry
    public boolean isTokenValid(String token) {
        try {
            extractClaims(token); // will throw exception if invalid
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    // Parse JWT and return claims
    // claims means the payload of the JWT which contains the information we put in the token (like subject, roles, etc.). When we parse the token, we can extract these claims to get the information we need for authentication and authorization.
    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
