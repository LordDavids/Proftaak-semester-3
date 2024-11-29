package com.api.productapi.dto;

import com.api.productapi.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {
    private Integer id;
    private String name;
    private long articleNumber;
    private BigDecimal price;
    private String description;
    private Boolean active;
    private int stock;
    private CategoryResponseDTO category;

    public ProductResponseDTO(Product product){
        this.id = product.getId();
        this.name = product.getName();
        this.articleNumber = product.getArticleNumber();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.active = product.getActive();
        this.stock = product.getStock();
        this.category = new CategoryResponseDTO(product.getCategory());

    }
}

