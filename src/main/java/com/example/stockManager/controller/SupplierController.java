package com.example.stockManager.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.example.stockManager.model.Supplier;
import com.example.stockManager.service.SupplierService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController 
@RequestMapping ("/api/suppliers")
public class SupplierController {
    private final SupplierService service;

    public SupplierController(SupplierService service) {
        this.service = service; // Constructor injection of the SupplierService
    }

    @PostMapping
    @ResponseStatus (HttpStatus.CREATED)
    public Supplier create(@RequestBody Supplier supplier) {
        return service.create(supplier); // Call the service to create a new supplier and return the created supplier
    }
    
}
