package com.api.productapi.repository;

import com.api.productapi.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface productRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findProductById(Integer id);
}
