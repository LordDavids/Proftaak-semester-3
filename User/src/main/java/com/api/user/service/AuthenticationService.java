package com.api.user.authentication;


public interface AuthenticationService {
    public AuthenticationResponseDTO register(RegisterRequestDTO registerRequestDTO);

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO authenticationRequestDTO);
}
