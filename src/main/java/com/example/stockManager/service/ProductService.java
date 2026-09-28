package com.example.stockManager.service;

import com.example.stockManager.exception.ProductNotFoundException;
import com.example.stockManager.model.Product;
import com.example.stockManager.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
    // The ProductService class is responsible for handling business logic related to products. It interacts with the ProductRepository to perform CRUD operations on Product entities.
    private final ProductRepository repo;

    // Constructor injection of the ProductRepository
    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    // Find a product by its ID, throwing an exception if not found
    public Product findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    // Retrieve all products from the repository
    public List<Product> findAll() {
        return repo.findAll(); // Call the repository to retrieve all products and return the list
    }

    // Create a new product in the repository
    public Product create(Product product) {
        return repo.save(product); // Call the repository to save the new product and return the saved product
    }

    // Delete a product by its ID, returning the deleted product
    public Product delete(Long id) {
        Product product = findById(id);
        repo.delete(product);
        return product;
    }

    // Update an existing product by its ID with new data
    public Product update(Long id, Product data) {
        
        Product product = findById(id);

        product.setName(data.getName());
        product.setPrice(data.getPrice());
        product.setStock(data.getStock());

        // Update product fields with data from 'data' object
        return repo.save(product);
    }
}

