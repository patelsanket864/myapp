package com.employee.myapp.utilities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtility {
    private final long FIXEDINTERVAL= 1000*60*60;
    private final String securityKey= "thisismysecretpleasekeepitsecret";
    private final SecretKey key =Keys.hmacShaKeyFor(securityKey.getBytes(StandardCharsets.UTF_8));

    public String generateJWT(String username){

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+FIXEDINTERVAL))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getBody().getSubject();
    }

    public Jws<Claims> extractClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }
    public boolean validateToken(String username, UserDetails userDetails, String token) {
        return username.equals(userDetails.getUsername()) && !tokenExpire(token);
    }

    private boolean tokenExpire(String token) {
        return extractClaims(token).getBody().getExpiration().before(new Date());
    }
}
