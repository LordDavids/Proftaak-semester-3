package com.api.productapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Setter
@Getter
@Table(name = "product")
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private int articleNumber;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    @Column(length = 200)
    private String description;
    @Column(nullable = false)
    private Boolean active;
    @Column(nullable = false)
    private int stock;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public Product() {}

}
