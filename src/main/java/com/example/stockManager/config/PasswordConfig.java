package com.example.stockManager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// Configuration class for password encoding
@Configuration

// Configures the password encoder bean for use in the application
public class PasswordConfig {
    // Bean definition for password encoder
    @Bean
    // Returns a BCryptPasswordEncoder instance for password encoding
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
