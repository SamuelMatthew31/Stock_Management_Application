package com.example.stockManager.controller;

import com.example.stockManager.service.ProductService; // Import the ProductService to handle product-related operations
import org.springframework.web.bind.annotation.GetMapping; // Import the GetMapping annotation to handle HTTP GET requests
import org.springframework.web.bind.annotation.PathVariable; // Import the PathVariable annotation to handle path variables in the request
import org.springframework.web.bind.annotation.RequestMapping; // Import the RequestMapping annotation to handle HTTP requests
import org.springframework.web.bind.annotation.RestController; // Import the RestController annotation to define a RESTful controller
import org.springframework.web.bind.annotation.PostMapping; // Import the PostMapping annotation to handle HTTP POST requests
import org.springframework.web.bind.annotation.RequestBody; // Import the RequestBody annotation to handle request body in the request
import org.springframework.web.bind.annotation.DeleteMapping; // Import the DeleteMapping annotation to handle HTTP DELETE requests
import org.springframework.web.bind.annotation.ResponseStatus; // Import the ResponseStatus annotation to handle response status in the request
import org.springframework.web.bind.annotation.PutMapping; // Import the PutMapping annotation to handle HTTP PUT requests
import com.example.stockManager.dto.ProductRequest; // Import the ProductRequest DTO to handle product request data
import com.example.stockManager.dto.ProductResponse; // Import the ProductResponse DTO to handle product response data
import org.springframework.http.HttpStatus; // Import the HttpStatus class to handle HTTP status codes
import java.util.List; // Import the List class to handle lists of products
import jakarta.validation.Valid; // Import the Valid annotation to validate product request data

@RestController // The RestController annotation indicates that this class is a controller that handles HTTP requests
@RequestMapping("/api/products") // The RequestMapping annotation specifies the base URL for all requests handled by this controller
public class ProductController {
    private final ProductService service; // The ProductService is injected via constructor injection

    public ProductController(ProductService service) {
        this.service = service; // Constructor injection of the ProductService
    }

    // Get product by ID
    @GetMapping("/{id}")
    public ProductResponse getById(@PathVariable Long id) {
        return service.findById(id); // Call the service to find the product by ID and return it
    }

    // Get all products
    @GetMapping
    public List<ProductResponse> getAll() {
        return service.findAll(); // Call the service to retrieve all products and return the list
    }

    // Create a new product
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    // Create a new product using the provided ProductRequest and return the created product
    public ProductResponse create(@Valid @RequestBody ProductRequest request) {
        return service.create(request); // Call the service to create a new product and return the created product
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
    public ProductResponse update(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
        return service.update(id, request); // Call the service to update the product by ID with the provided product data and return the updated product
    }
}
