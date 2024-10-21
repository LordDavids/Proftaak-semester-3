package com.api.user.service;

import java.util.Map;

public interface JwtService {

    public boolean validateToken(String token);
    public boolean isTokenExpired(String token);
    public String getIdFromToken(String token);
    public String GenerateToken(Map<String, Object> claims, Long id);
}
