package com.api.productapi.controller;

import com.api.productapi.dto.CategoryResponseDTO;
import com.api.productapi.entities.Category;
import com.api.productapi.repository.CategoryRepository;
import com.api.productapi.service.CategoryService;
import com.api.productapi.service.impl.CategoryServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService CategoryService;

    public CategoryController(CategoryService CategoryService) {
        this.CategoryService = CategoryService;
    }

    @GetMapping
    public ResponseEntity<Object> GetAllCategory() {
        return ResponseEntity.ok(CategoryService.getAllCategories());
    }
}
