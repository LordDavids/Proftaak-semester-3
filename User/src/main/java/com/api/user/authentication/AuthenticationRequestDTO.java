package com.api.user.authentication;

public record AuthenticationRequestDTO (
     String email,
    String password
)
{}
