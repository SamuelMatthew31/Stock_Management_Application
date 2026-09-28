package com.example.stockManager.repository;

import com.example.stockManager.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    
}
