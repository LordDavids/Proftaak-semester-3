package com.api.productapi.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
