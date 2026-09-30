package com.example.stockManager.mapper;

import com.example.stockManager.model.Product;
import com.example.stockManager.model.Supplier;
import com.example.stockManager.dto.ProductRequest;
import com.example.stockManager.dto.ProductResponse;

// Mapper class for converting between ProductRequest and Product entities
public class ProductMapper {
    // Converts a ProductRequest to a Product entity
    public static Product toEntity(ProductRequest request, Supplier supplier) {
        // Creates a new Product entity from the request
        Product product = new Product(request.name(), request.stock(), request.price());
        // Sets the supplier for the product
        product.setSupplier(supplier);
        // Returns the product entity
        return product;
    }
    // Converts a Product entity to a ProductResponse
    public static ProductResponse toResponse(Product product) {
        // Returns the product response
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getStock(),
                product.getPrice(),
                product.getSupplier().getName()
        );
    }
}
