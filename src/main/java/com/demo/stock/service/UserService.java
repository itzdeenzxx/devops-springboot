package com.demo.stock.service;

import com.demo.stock.model.User;
import com.demo.stock.repository.UserRepository;
import com.demo.stock.error.MissingParameterException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getUsers() {
        return repository.findAll();
    }

    public User getUserById(UUID id) {
        return repository.findById(id);
    }

    public void createUser(String name, String email, String password, String role) {
        if (name == null || name.isBlank()) {
            throw new MissingParameterException("name");
        }
        if (email == null || email.isBlank()) {
            throw new MissingParameterException("email");
        }
        if (password == null || password.isBlank()) {
            throw new MissingParameterException("password");
        }

        UUID id = UUID.randomUUID();
        String passwordHash = hashPassword(password);
        Timestamp createdAt = Timestamp.valueOf(LocalDateTime.now());

        repository.createUser(id, name, email, passwordHash, role, createdAt);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to hash password", e);
        }
    }
}