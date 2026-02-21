package com.demo.stock.repository;

import com.demo.stock.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Product> productRowMapper = (rs, rowNum) -> {
        Product product = new Product(
            UUID.fromString(rs.getString("id")),
            UUID.fromString(rs.getString("user_id")),
            rs.getString("title"),
            rs.getString("description"),
            rs.getBigDecimal("price"),
            rs.getDouble("rating"),
            rs.getString("image_url"),
            rs.getString("category"),
            rs.getTimestamp("created_at").toLocalDateTime()
        );
        try {
            product.setFreelancerName(rs.getString("freelancer_name"));
        } catch (Exception ignored) {}
        return product;
    };

    public List<Product> findAll() {
        String sql = "SELECT p.id, p.user_id, p.title, p.description, p.price, "
                + "p.rating, p.image_url, p.category, p.created_at, "
                + "u.name AS freelancer_name "
                + "FROM products p "
                + "JOIN users u ON p.user_id = u.id "
                + "ORDER BY p.created_at DESC";
        return jdbcTemplate.query(sql, productRowMapper);
    }

    public Product findById(UUID id) {
        String sql = "SELECT p.id, p.user_id, p.title, p.description, p.price, "
                + "p.rating, p.image_url, p.category, p.created_at, "
                + "u.name AS freelancer_name "
                + "FROM products p "
                + "JOIN users u ON p.user_id = u.id "
                + "WHERE p.id = ?::uuid";
        return jdbcTemplate.queryForObject(sql, productRowMapper, id.toString());
    }

    public List<Product> findByUserId(UUID userId) {
        String sql = "SELECT p.id, p.user_id, p.title, p.description, p.price, "
                + "p.rating, p.image_url, p.category, p.created_at, "
                + "u.name AS freelancer_name "
                + "FROM products p "
                + "JOIN users u ON p.user_id = u.id "
                + "WHERE p.user_id = ?::uuid "
                + "ORDER BY p.created_at DESC";
        return jdbcTemplate.query(sql, productRowMapper, userId.toString());
    }

    public int create(UUID id, UUID userId, String title, String description,
                      BigDecimal price, String imageUrl, String category, Timestamp createdAt) {
        String sql = "INSERT INTO products (id, user_id, title, description, price, rating, image_url, category, created_at) "
                + "VALUES (?::uuid, ?::uuid, ?, ?, ?, 0, ?, ?, ?)";
        return jdbcTemplate.update(sql, id.toString(), userId.toString(),
                title, description, price, imageUrl, category, createdAt);
    }

    public int delete(UUID id) {
        String sql = "DELETE FROM products WHERE id = ?::uuid";
        return jdbcTemplate.update(sql, id.toString());
    }
}
