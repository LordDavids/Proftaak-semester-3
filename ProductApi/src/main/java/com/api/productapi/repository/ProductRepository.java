package com.api.productapi.repository;

import com.api.productapi.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findProductById(Integer id);

    @Query("SELECT p.stock FROM Product p WHERE p.id = :id")
    Integer findStockById(@Param("id") int id);

    @Query("SELECT p FROM Product p WHERE p.category.category_id = :categoryId AND p.active = true")
    Page<Product> findProductByCategoryId(Integer categoryId, Pageable pageable);


}
