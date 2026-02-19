package com.example.SpringEcommerceBackend.secuirty;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    // ⚠ In production move this to application.yml
    private static final String SECRET_KEY =
            "your_super_secret_key_your_super_secret_key_123456";

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(
                java.util.Base64.getEncoder().encodeToString(SECRET_KEY.getBytes())
        );
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // generate a JWT token with the provided email as the subject, set the issued at and expiration time, and sign it with the signing key
    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(getSigningKey())
                .compact();
    }

    // get the email from the token by extracting the claims and returning the subject (email) from the claims
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // validate the token by checking if the email in the token matches the provided email and if the token is not expired
    public boolean isTokenValid(String token, String email) {
        final String extractedEmail = extractEmail(token);
        return (extractedEmail.equals(email) && !isTokenExpired(token));
    }

    // check token expiration by extrating the claims and checking the expiration date against the current date
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration)
                .before(new Date());
    }


    // extract claims( like email , expiration etc ) from the token using the provided claims resolver function
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claimsResolver.apply(claims);
    }
}
