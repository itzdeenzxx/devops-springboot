package com.demo.stock.service;

import com.demo.stock.error.MissingParameterException;
import com.demo.stock.model.Product;
import com.demo.stock.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Product getProductById(UUID id) {
        return repository.findById(id);
    }

    public List<Product> getProductsByUserId(UUID userId) {
        return repository.findByUserId(userId);
    }

    public void createProduct(UUID userId, String title, String description,
                              BigDecimal price, String imageUrl, String category) {
        if (userId == null) {
            throw new MissingParameterException("userId");
        }
        if (title == null || title.isBlank()) {
            throw new MissingParameterException("title");
        }
        if (price == null) {
            throw new MissingParameterException("price");
        }

        UUID id = UUID.randomUUID();
        Timestamp createdAt = Timestamp.valueOf(LocalDateTime.now());
        repository.create(id, userId, title, description, price, imageUrl, category, createdAt);
    }

    public void deleteProduct(UUID id) {
        repository.delete(id);
    }
}
