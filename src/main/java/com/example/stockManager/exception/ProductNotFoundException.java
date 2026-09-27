package com.example.stockManager.exception;

// This exception is thrown when a product with a specified ID is not found in the system.
public class ProductNotFoundException extends RuntimeException {
    // Constructor that takes the ID of the product that was not found and constructs an error message.
    public ProductNotFoundException(Long id) {
        super("Produk dengan id " + id + " tidak ditemukan");
    }
}