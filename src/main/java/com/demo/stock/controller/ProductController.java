package com.demo.stock.controller;

import com.demo.stock.dto.CreateProductRequest;
import com.demo.stock.model.Product;
import com.demo.stock.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // GET /products
    @GetMapping
    public List<Product> getAllProducts() {
        return service.getAllProducts();
    }

    // GET /products/{id}
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable UUID id) {
        return service.getProductById(id);
    }

    // GET /products/user/{userId}
    @GetMapping("/user/{userId}")
    public List<Product> getProductsByUser(@PathVariable UUID userId) {
        return service.getProductsByUserId(userId);
    }

    // POST /products
    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody CreateProductRequest request) {
        service.createProduct(
            request.getUserId(),
            request.getTitle(),
            request.getDescription(),
            request.getPrice(),
            request.getImageUrl(),
            request.getCategory()
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // DELETE /products/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        service.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
