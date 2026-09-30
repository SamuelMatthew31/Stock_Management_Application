package com.example.stockManager.dto;

import java.math.BigDecimal;

// the response DTO for a product
// using record to create a simple, immutable DTO which automatically generates getters and toString methods
public record ProductResponse (
    Long id,
    String name,
    int stock,
    BigDecimal price,
    String supplierName

    //the field is automatically finalized and cannot be modified after creation
) { }
