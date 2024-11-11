package com.api.user.service;

import java.util.Map;

public interface JwtService {

    boolean validateToken(String token);
    boolean isTokenExpired(String token);
    String getIdFromToken(String token);
    String generateToken(Map<String, Object> claims, Long id);
}
