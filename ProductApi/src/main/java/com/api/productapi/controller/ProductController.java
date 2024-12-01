package com.api.productapi.controller;
import com.api.productapi.dto.ProductPageDTO;
import com.api.productapi.dto.ProductResponseDTO;
import com.api.productapi.service.ProductService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    //private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Get products product list by category ID for product select page
    @GetMapping("/{categoryId}/{page}/{size}")
    public ResponseEntity<ProductPageDTO> getProducts(@PathVariable int categoryId
            , @PathVariable int page, @PathVariable int size) throws InterruptedException {
//        logger.info("Request started. Category ID: {}", categoryId);
//        Thread.sleep(10000);  // Sleep for 10 seconds
        ProductPageDTO productPageDTO = productService.getProductsByCategoryId(categoryId, page, size);
//        logger.info("Request completed. Category ID: {}", categoryId);
        return new ResponseEntity<>(productPageDTO, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable int productId) throws InterruptedException {
        try {
            ProductResponseDTO productResponseDTO = productService.getProductById(productId);
            return new ResponseEntity<>(productResponseDTO, HttpStatus.OK);
        }
        catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}
