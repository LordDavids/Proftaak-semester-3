package com.api.productapi.controller;

import com.api.productapi.entities.Product;
import com.api.productapi.repository.productRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/product/demo")
public class DemoProductController {

    public DemoProductController() {
    }

    @GetMapping
    public ResponseEntity<String> demo() {
        String hallo = "test voor product api ";
        return ResponseEntity.ok(hallo );
    }
}
