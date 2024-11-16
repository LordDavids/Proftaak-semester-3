package com.api.productapi.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Setter
@Getter
@Table(name = "product")
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
    @Column( length = 200)
    private String description;
    @Column(nullable = false)
    private Boolean active;
    @Column(nullable = false)
    private int stock;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public Product() {
    }
    public Product(Integer id, String name, int articleNumber, BigDecimal price, String description, Boolean active, int stock, Category category) {
        this.id = id;
        this.name = name;
        this.articleNumber = articleNumber;
        this.price = price;
        this.description = description;
        this.active = active;
        this.stock = stock;
        this.category = category;
    }

}
