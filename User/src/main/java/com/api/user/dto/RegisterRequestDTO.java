package com.api.user.authentication;

public record RegisterRequestDTO (
    String first_name,
    String lastname,
    String email,
    String password

){}


