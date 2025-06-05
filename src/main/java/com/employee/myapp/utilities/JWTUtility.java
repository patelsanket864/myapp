package com.employee.myapp.utilities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JWTUtility {
    private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 1000; // 5 hours

    @Value("${jwt.secret}") // Load secret from application.properties/yml
    private String secretKey;

    // Method to get the signing key from the secret (consistent with API Gateway)
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey); // Decode from Base64
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateJWT(String username){ // Renamed from generateJWT for clarity

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY)) // Use JWT_TOKEN_VALIDITY
                .signWith(getSignKey(), SignatureAlgorithm.HS256) // Use getSignKey()
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject); // Use extractClaim for consistency
    }

    public Date extractExpiration(String token) { // Added for consistency
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) { // Added for consistency
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) { // Added for consistency
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String username, UserDetails userDetails, String token) {
        final String extractedUsername = extractUsername(token); // Extract username from token
        return extractedUsername.equals(userDetails.getUsername()) && !isTokenExpired(token); // Use isTokenExpired
    }

    private boolean isTokenExpired(String token) { // Renamed from tokenExpire for consistency
        return extractExpiration(token).before(new Date());
    }
}
