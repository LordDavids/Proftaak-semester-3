package com.api.user.user;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String first_name;
    @Column(nullable = false)
    private String lastname;
    @Column(nullable = false, unique = true, length = 320)
    private String email;
    @Column(nullable = false)
    private String password;

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


    public String getUsername() {
        return email;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getFirst_name() {return first_name;}
    public void setFirst_name(String first_name) {this.first_name = first_name;}

    public String getLastname() {return lastname;}
    public void setLastname(String lastname) {this.lastname = lastname;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}


    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
}
