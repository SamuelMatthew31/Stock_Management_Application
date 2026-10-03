package com.example.stockManager.repository;

import com.example.stockManager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// Repository interface for User entity
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username); // find user by username
}
