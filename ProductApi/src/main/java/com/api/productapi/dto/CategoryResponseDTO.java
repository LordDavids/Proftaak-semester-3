package com.api.productapi.dto;

import com.api.productapi.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDTO {
    int id;
    String name;

    public CategoryResponseDTO(Category category){
        this.id = category.getCategory_id();
        this.name = category.getName();
    }
}
