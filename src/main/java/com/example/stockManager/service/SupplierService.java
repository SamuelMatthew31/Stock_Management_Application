package com.example.stockManager.service;

import com.example.stockManager.repository.SupplierRepository;
import com.example.stockManager.model.Supplier;
import org.springframework.stereotype.Service;

// The SupplierService class is responsible for handling business logic related to suppliers. It interacts with the SupplierRepository to perform CRUD operations on Supplier entities.
@Service
public class SupplierService {
    // The SupplierService class is responsible for handling business logic related to suppliers. It interacts with the SupplierRepository to perform CRUD operations on Supplier entities.
    private final SupplierRepository repo;

    // Constructor injection of the SupplierRepository
    public SupplierService(SupplierRepository repo) {
        this.repo = repo;
    }
    // Create a new supplier in the repository
    public Supplier create(Supplier supplier) {
        return repo.save(supplier); // Call the repository to save the new supplier and return the saved supplier
    }
}
