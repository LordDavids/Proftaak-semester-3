package com.api.productapi.service.impl;

import com.api.productapi.service.JwtService;
import com.api.productapi.entities.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    private final SecretKey secretKey;

    public JwtServiceImpl(@Value("${security.jwt.secret-key}")String base64SecretKey) {
        byte[] keyBytes = Base64.getDecoder().decode(base64SecretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }


    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }

    public JwtUser getUserFromToken(String token) {
        JwtUser user = new JwtUser();
        user.setId(Long.parseLong(getIdFromToken(token)));
        user.setRole(getRoleFromToken(token));
        return user;
    }

    public String getIdFromToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("sub", String.class);
    }

    public String getRoleFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)  // Use your secret key
                .build()
                .parseSignedClaims(token)
                .getPayload();

        // Retrieve the "roles" claim as a list
        return "ROLE_" + claims.get("roles", String.class);
    }

}