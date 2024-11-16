package com.api.productapi.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class User {

    private Long id;
    private String first_name;
    private String lastname;
    private String email;
    private String password;
    private String phoneNumber;
    private Role role;

    public User() {}

    public User(Long id, String firstName, String lastname, String email, String password, String phoneNumber) {
        this.id = id;
        this.first_name = firstName;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase()));
    }
}
