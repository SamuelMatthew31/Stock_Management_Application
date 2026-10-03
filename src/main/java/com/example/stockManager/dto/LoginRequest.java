package com.example.stockManager.dto;

import jakarta.validation.constraints.NotBlank;

// Data Transfer Object for login requests
public record LoginRequest(
    // Username for login
    @NotBlank String username,
    // Password for login
    @NotBlank String password
) { }
