package com.api.productapi.service.impl;


import com.api.productapi.entities.Category;
import com.api.productapi.repository.CategoryRepository;
import com.api.productapi.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository CategoryRepository;

    public CategoryServiceImpl(CategoryRepository CategoryRepository) {
        this.CategoryRepository = CategoryRepository;
    }

    public List<Category> getAllCategories() {
        return CategoryRepository.findAll();
    }

}
