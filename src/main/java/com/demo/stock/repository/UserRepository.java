package com.demo.stock.repository;

import com.demo.stock.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> findAll() {
        String sql = "SELECT id, name, email, role, created_at FROM users";
        return jdbcTemplate.query(
            sql,
            (rs, rowNum) -> new User(
                UUID.fromString(rs.getString("id")),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("role"),
                rs.getTimestamp("created_at").toLocalDateTime()
            )
        );
    }

    public User findById(UUID id) {
        String sql = "SELECT id, name, email, role, created_at FROM users WHERE id = ?::uuid";
        return jdbcTemplate.queryForObject(
            sql,
            (rs, rowNum) -> new User(
                UUID.fromString(rs.getString("id")),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("role"),
                rs.getTimestamp("created_at").toLocalDateTime()
            ),
            id.toString()
        );
    }

    public int createUser(UUID id, String name, String email, String passwordHash, String role, Timestamp createdAt) {
        String sql = "INSERT INTO users (id, name, email, password_hash, role, created_at) VALUES (?::uuid, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, id.toString(), name, email, passwordHash, role, createdAt);
    }
}