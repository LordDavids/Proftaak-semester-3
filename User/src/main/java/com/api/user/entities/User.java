package com.api.user.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;
    @Setter
    @Getter
    @Column(name = "first_name", nullable = false)
    private String first_name;
    @Setter
    @Getter
    @Column(nullable = false)
    private String lastname;
    @Setter
    @Getter
    @Column(nullable = false, unique = true, length = 320)
    private String email;
    @Setter
    @Getter
    @Column(nullable = false)
    private String password;

    @Getter
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    public User() {}

    public User(Long id, String first_name, String lastname, String email, String password) {
        this.id = id;
        this.first_name = first_name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }
    public User(String first_name, String lastname, String email, String password, Role role) {
        this.first_name = first_name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase()));
    }
}
