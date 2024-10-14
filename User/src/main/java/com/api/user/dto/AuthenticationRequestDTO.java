package com.api.user.dto;

public record AuthenticationRequestDTO (
    String email,
    String password
    ) {}
