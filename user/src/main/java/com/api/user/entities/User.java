package com.api.user.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
@Entity
@Table(name = "user")
public class User {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;
    @Setter
    @Column(name = "first_name", nullable = false)
    private String first_name;
    @Setter
    @Column(nullable = false)
    private String lastname;
    @Setter
    @Column(nullable = false, unique = true, length = 320)
    private String email;
    @Setter
    @Column(nullable = false)
    private String password;
    @Setter
    @Column(nullable = false, length = 25)
    private String phoneNumber;
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    public User() {}

    public User(Long id, String firstName, String lastname, String email, String password,String phoneNumber) {
        this.id = id;
        this.first_name = firstName;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
    public User(String firstName, String lastname, String email, String password, Role role, String phoneNumber) {
        this.first_name = firstName;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.phoneNumber = phoneNumber;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase()));
    }
}
