package com.example.stockManager.service;

import com.example.stockManager.dto.RegisterRequest;
import com.example.stockManager.model.Role;
import com.example.stockManager.model.User;
import com.example.stockManager.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service // Service class for handling authentication-related operations
// Authentication service for handling user registration and authentication
public class AuthService {
    private final UserRepository userRepo; // Repository for user data
    private final PasswordEncoder passwordEncoder; // Password encoder for hashing passwords

    // Constructor for AuthService
    public AuthService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo; // Repository for user data
        this.passwordEncoder = passwordEncoder; // Password encoder for hashing passwords
    }

    // Registers a new user based on the provided registration request
    public User register(RegisterRequest request) {
        String hashedPassword = passwordEncoder.encode(request.password()); // Hash the password
        User user = new User(request.username(), hashedPassword, Role.STAFF); // Create a new user
        return userRepo.save(user); // Save the user to the repository
    }
}
