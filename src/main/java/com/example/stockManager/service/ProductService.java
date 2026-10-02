        package com.example.stockManager.service;

import com.example.stockManager.dto.ProductRequest;
import com.example.stockManager.exception.ProductNotFoundException;
import com.example.stockManager.model.Product;
import com.example.stockManager.model.Supplier;
import com.example.stockManager.repository.ProductRepository;
import com.example.stockManager.repository.SupplierRepository;
import org.springframework.stereotype.Service;
import com.example.stockManager.exception.SupplierNotFoundException;
import com.example.stockManager.dto.ProductResponse;
import com.example.stockManager.mapper.ProductMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class ProductService {
    // The ProductService class is responsible for handling business logic related to products. It interacts with the ProductRepository to perform CRUD operations on Product entities.

    private final ProductRepository repo; // Declare a private final field for the ProductRepository, which will be used to interact with the database.

    private final SupplierRepository supplierRepo; // Declare a private final field for the SupplierRepository, which will be used to interact with suppliers.

    // Constructor injection of the ProductRepository
    public ProductService(ProductRepository repo, SupplierRepository supplierRepo) {
        this.repo = repo;
        this.supplierRepo = supplierRepo;
    }

    // Find a product by its ID, throwing an exception if not found
    public ProductResponse findById(Long id) {
        return ProductMapper.toResponse(getProductEntity(id)); // Map the product entity to a response and return it
    }

    // Find a supplier by its ID, throwing an exception if not found
    public Supplier findSupplierById(Long id) {
        return supplierRepo.findById(id) // Call the repository to find the supplier by ID
                .orElseThrow(() -> new SupplierNotFoundException(id)); // Throw an exception if the supplier is not found
    }

    // Find all products by name, with pagination support
    public Page<ProductResponse> findAll(String name, Pageable pageable) {
        // If the name is null or empty, return all products; otherwise, return products matching the name
        if (name == null || name.isBlank()) {
            return repo.findAll(pageable).map(ProductMapper::toResponse); // Return all products
        } else {
            return repo.findByNameContainingIgnoreCase(name, pageable).map(ProductMapper::toResponse); // Return products matching the name
        }
    }

    // Find a product by its ID, throwing an exception if not found
    private Product getProductEntity(Long id) {
        return repo.findById(id) // Call the repository to find the product by ID
                .orElseThrow(() -> new ProductNotFoundException(id)); // Throw an exception if the product is not found
    }

    // Create a new product in the repository
    public ProductResponse create(ProductRequest request) {
        Supplier supplier = findSupplierById(request.supplierId()); // Find the supplier by ID to ensure it exists
        Product product = ProductMapper.toEntity(request, supplier); // Map the request data to a product entity
        return ProductMapper.toResponse(repo.save(product)); // Map the saved product to a response and return it
    }

    // Delete a product by its ID, returning the deleted product
    public void delete(Long id) {
        Product product = getProductEntity(id); // Find the product by ID to ensure it exists
        repo.delete(product); // Delete the product from the repository
    }

    // Update an existing product by its ID with new data
    public ProductResponse update(Long id, ProductRequest data) {
        Product product = getProductEntity(id); // Find the product by ID to ensure it exists
        Supplier supplier = findSupplierById(data.supplierId()); // Find the supplier by ID to ensure it exists
        product.setName(data.name()); // Update the product name
        product.setPrice(data.price()); // Update the product price
        product.setStock(data.stock()); // Update the product stock
        product.setSupplier(supplier); // Update the product supplier
        return ProductMapper.toResponse(repo.save(product)); // Map the updated product to a response and return it
    }
}
