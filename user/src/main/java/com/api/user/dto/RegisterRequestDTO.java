package com.api.user.dto;

public record RegisterRequestDTO(
    String first_name,
    String lastname,
    String email,
    String password,
    String phone_number

){}


