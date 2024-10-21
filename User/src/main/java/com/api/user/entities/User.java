package com.api.user.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

}
