package com.api.productapi.service.impl;

import com.api.productapi.dto.ProductPageDTO;
import com.api.productapi.dto.ProductResponseDTO;
import com.api.productapi.entities.Product;
import com.api.productapi.repository.ProductRepository;
import com.api.productapi.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.apache.coyote.BadRequestException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;

    }

    public ProductPageDTO getProductsByCategoryId(int id ,int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.findProductByCategoryId(id,pageable);
        List<ProductResponseDTO> productsList = new ArrayList<>();
        for (Product product : products) {
            productsList.add(new ProductResponseDTO(product));
        }
        return new ProductPageDTO(productsList, products.getTotalPages(), products.getTotalElements());
    }

    public ProductResponseDTO getProductById(int id) throws BadRequestException {
        var product = productRepository.findById(id).orElse(null);
        if(product == null) {
            throw new BadRequestException("Product not found");
        }
        return new ProductResponseDTO(product);
    }

}
