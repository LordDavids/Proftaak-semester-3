package com.api.productapi.service;

import com.api.productapi.entities.JwtUser;
import org.springframework.stereotype.Component;

@Component
public interface JwtService {

    boolean validateToken(String token);
    boolean isTokenExpired(String token);
    String getIdFromToken(String token);
    JwtUser getUserFromToken(String token);
}
