package com.api.productapi.service;

import com.api.productapi.dto.ProductPageDTO;
import com.api.productapi.dto.ProductResponseDTO;
import org.apache.coyote.BadRequestException;

import java.util.Optional;

public interface ProductService {

    ProductPageDTO getProductsByCategoryId(int id , int page, int size);
    ProductResponseDTO getProductById(int id) throws BadRequestException;
    Optional<Integer> getProductStock(int id);

}
