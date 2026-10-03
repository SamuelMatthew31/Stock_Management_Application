package com.example.stockManager.model;

import jakarta.persistence.*; // Import JPA annotations

// User entity
@Entity
// Represents a user in the system
public class User {

    // Primary key for the user
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Unique username for the user
    @Column(unique = true, nullable = false)
    private String username;

    // Password for the user
    @Column(nullable = false)
    private String password;

    // Role of the user (ADMIN or STAFF)
    @Enumerated(EnumType.STRING)
    private Role role;

    protected User() { } // Default constructor

    // Constructor to create a new user
    public User(String usernmae, String password, Role role) {
        this.username = username; // Set the username
        this.password = password; // Set the password
        this.role = role; // Set the role
    }

    public Long getId() { return id; } // Get the user ID
    public String getUsername() { return username; } // Get the username
    public String getPassword() { return password; } // Get the password
    public Role getRole() { return role; } // Get the role
}
