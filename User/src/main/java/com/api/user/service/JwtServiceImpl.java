package com.api.user.service;

import com.api.user.entities.jwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    @Value("${security.jwt.secret-key}")
    private String secretKeyString;

    public SecretKey secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKeyString));

    public JwtServiceImpl() {
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

    public jwtUser getUserFromToken(String token) {
        jwtUser user = new jwtUser();
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


    public String GenerateToken(Map<String, Object> claims, Long id) {
            return Jwts
                    .builder()
                    .claims(claims)
                    .subject(id.toString())
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                    .signWith(secretKey, Jwts.SIG.HS256)
                    .compact();

    }


}