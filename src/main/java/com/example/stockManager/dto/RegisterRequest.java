package com.example.stockManager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// DTO for registration request
public record RegisterRequest(

    // Username field
    @NotBlank(message = "Username tidak boleh kosong") // Username field cannot be blank
    String username, //

    // Password field
    @NotBlank(message = "Password tidak boleh kosong") // Password field cannot be blank
    @Size(min = 6, message ="Password minimal 6 karakter") // Password field must be at least 6 characters
    String password
) { }
