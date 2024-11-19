package com.api.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(
    @NotNull
    @Size(min = 2, max = 255)
    String first_name,
    @NotNull
    @Size(min = 2, max = 255)
    String lastname,
    @NotNull
    @Size(min = 2, max = 320)
    String email,
    String password,
    @NotNull
    @Size(min = 2,max = 25)
    String phone_number

){}


