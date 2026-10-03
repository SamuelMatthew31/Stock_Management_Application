package com.example.stockManager.security;

import io.jsonwebtoken.Claims; // for parsing JWT claims
import io.jsonwebtoken.Jwts; // for JWT operations
import io.jsonwebtoken.security.Keys; // for generating HMAC keys
import org.springframework.stereotype.Component; // for Spring component scanning

import javax.crypto.SecretKey; // for HMAC key generation
import java.util.Date; // for date operations

@Component // for Spring component scanning

// Utility class for JWT operations
public class JWUtil {
    // Secret key for HMAC signing
    private final SecretKey key = Keys.hmacShaKeyFor(
        "ganti-dengan-string-rahasia-minimal-32-karakter-panjangnya".getBytes() // Secret key for HMAC signing
    );

    // Expiration time for JWT tokens
    private final long EXPIRATION_MS = 1000 * 60 * 60; // 1 hour

    // Generates a JWT token for the given username
    public String generateToker(String username) {
        return Jwts.builder()
            .subject(username) // Sets the subject of the token to the username
            .issuedAt(new Date()) // Sets the issued at time of the token to the current time
            .expiration(new Date(System.currentTimeMillis() + EXPIRATION_MS)) // Sets the expiration time of the token
            .signWith(key) // Signs the token with the secret key
            .compact(); // Compacts the token into a string
    }

    // Extracts the username from the JWT token
    public String extractUsername(String token) {
        return parseClaims(token).getSubject(); // Extracts the subject (username) from the token
    }

    // Validates the JWT token
    public boolean isTokenValid(String token) {
        try { // Tries to parse and validate the token
            parseClaims(token); // Parses and validates the token
            return true; // Token is valid
        } catch (Exception e) { // Token is invalid
            return false; // Returns false if the token is invalid
        }
    }

    // Parses and validates the JWT token
    private Claims parseClaims(String token) {
        return Jwts.parser() // Parses the token
            .verifyWith(key) // Verifies the token with the secret key
            .build() // Builds the parser
            .parseSignedClaims(token) // Parses the signed claims from the token
            .getPayload(); // Returns the payload (claims) of the token
    }
}
