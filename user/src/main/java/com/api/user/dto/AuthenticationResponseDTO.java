package com.api.user.dto;

import com.api.user.entities.Role;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AuthenticationResponseDTO{
        private String token;
        private String firstName;
        private String lastName;
        private String email;
        private Role role;
        private String phoneNumber;

        public AuthenticationResponseDTO(String jwtToken, String firstName, String lastName, String email, Role role, String phoneNumber) {
                this.token = jwtToken;
                this.firstName = firstName;
                this.lastName = lastName;
                this.email = email;
                this.role = role;
                this.phoneNumber = phoneNumber;

        }
}
