package com.example.stockManager.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record ProductRequest(
    @NotBlank String name,

    @NotNull(message = "Stok tidak boleh kosong")
    @Min(value = 0, message = "Stok tidak boleh negatif")
    Integer stock,

    @NotNull(message = "Harga tidak boleh kosong")
    @DecimalMin(value = "0.0", message = "Harga tidak boleh negatif")
    BigDecimal price,

    @NotNull(message = "Supplier tidak boleh kosong")
    Long supplierId
) { }
