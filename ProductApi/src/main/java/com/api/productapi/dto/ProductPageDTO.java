package com.api.productapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductPageDTO {
    private List<ProductResponseDTO> products;
    private int totalPages;
    private long totalElements;
}
