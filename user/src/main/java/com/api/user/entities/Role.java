package com.api.user.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer id;
    @Column(nullable = false)
    private String name;

    public Role() {

    }
    public Role(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

}
