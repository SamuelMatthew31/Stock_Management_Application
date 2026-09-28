package com.example.stockManager.controller;

import com.example.stockManager.model.Product;
import com.example.stockManager.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    // The ProductController class is responsible for handling HTTP requests related to products. It uses the ProductService to perform operations on Product entities.
    private final ProductService service;
    
    public ProductController(ProductService service) {
        this.service = service; // Constructor injection of the ProductService
    }

    // Get product by ID
    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return service.findById(id); // Call the service to find the product by ID and return it
    }

    // Get all products
    @GetMapping
    public List<Product> getAll() {
        return (List<Product>) service.findAll(); // Call the service to retrieve all products and return the list
    }
    
    // Create a new product
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@Valid @RequestBody Product product) {
        return service.create(product); // Call the service to create a new product and return the created product
    }

    // Delete a product by ID
    @DeleteMapping("/{id}")
    @ResponseStatus (HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id); // Call the service to delete the product by ID
    }

    // Update a product by ID
    @PutMapping("/{id}")
    @ResponseStatus (HttpStatus.OK)
    public Product update(@PathVariable Long id, @Valid @RequestBody Product product) {
        return service.update(id, product); // Call the service to update the product by ID with the provided product data and return the updated product
    }
}

