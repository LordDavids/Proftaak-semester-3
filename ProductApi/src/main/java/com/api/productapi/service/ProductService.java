package com.api.productapi.service;

import com.api.productapi.dto.ProductPageDTO;
import com.api.productapi.dto.ProductResponseDTO;
import org.apache.coyote.BadRequestException;

public interface ProductService {

    ProductPageDTO getProductsByCategoryId(int id , int page, int size);
    ProductResponseDTO getProductById(int id) throws BadRequestException;

}
