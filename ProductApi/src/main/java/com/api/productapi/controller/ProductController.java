package com.api.productapi.controller;
import com.api.productapi.dto.ProductPageDTO;
import com.api.productapi.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
@EnableAsync
public class ProductController {

    //private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public ResponseEntity<ProductPageDTO> GetProducts(@RequestParam int categoryId
                                                        ,@RequestParam int page,
                                                        @RequestParam int size) throws InterruptedException {
//        logger.info("Request started. Category ID: {}", categoryId);
//        Thread.sleep(10000);  // Sleep for 10 seconds
        ProductPageDTO productPageDTO = productService.getProductsByCategoryId(categoryId, page, size);
//        logger.info("Request completed. Category ID: {}", categoryId);
        return new ResponseEntity<>(productPageDTO, HttpStatus.OK);
    }



}
