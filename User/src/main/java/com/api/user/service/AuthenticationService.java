package com.api.user.service;


import com.api.user.dto.AuthenticationRequestDTO;
import com.api.user.dto.AuthenticationResponseDTO;
import com.api.user.dto.RegisterRequestDTO;

public interface AuthenticationService {
    public AuthenticationResponseDTO register(RegisterRequestDTO registerRequestDTO);

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO authenticationRequestDTO);
}
