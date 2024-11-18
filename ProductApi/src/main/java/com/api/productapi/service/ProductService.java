package com.api.productapi.service;

import com.api.productapi.dto.ProductPageDTO;
import org.springframework.stereotype.Service;

public interface ProductService {

    ProductPageDTO getProductsByCategoryId(int id , int page, int size);

}
