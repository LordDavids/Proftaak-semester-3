package com.api.user.jwt;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {

    public boolean validateToken(String token);
    public boolean isTokenExpired(String token);
    public String getEmailFromToken(String token);
    public String GenerateToken(Map<String, Object> claims, String email);
}
