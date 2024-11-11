package com.api.user.entities;

import com.api.user.dto.AuthenticationResponseDTO;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Auth {
    private String firstName;
    private String lastName;
    private String email;
    private Role role;

    public Auth(AuthenticationResponseDTO auth) {
        this.firstName = auth.getFirstName();
        this.lastName = auth.getLastName();
        this.email = auth.getEmail();
        this.role = auth.getRole();
    }
}
