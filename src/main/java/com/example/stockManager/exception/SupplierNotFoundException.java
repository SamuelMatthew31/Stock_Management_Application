package com.example.stockManager.exception;

public class SupplierNotFoundException extends RuntimeException {
    public SupplierNotFoundException(Long id) {
        super("Supplier dengan id " + id + " tidak ditemukan");
    }
}