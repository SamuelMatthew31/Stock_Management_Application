package com.example.stockManager.service;

import com.example.stockManager.dto.ProductResponse;
import com.example.stockManager.model.Product;
import com.example.stockManager.model.Supplier;
import com.example.stockManager.repository.ProductRepository;
import com.example.stockManager.repository.SupplierRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.stockManager.exception.ProductNotFoundException;
import com.example.stockManager.dto.ProductRequest;
import com.example.stockManager.exception.SupplierNotFoundException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository repo;

    @Mock
    private SupplierRepository supplierRepo;

    @InjectMocks
    private ProductService service;

    @Test
    // Verifies that findById returns a ProductResponse when the product exists
    void findById_WhenProductExists_returnsProductResponse() {
        // Arrange: create a supplier and product, then mock the repository to return the product
        Supplier supplier = new Supplier("PT SUmber Pangan", "Bandung");
        // Create a product and set its supplier
        Product product = new Product("Gula Pasir", 10, new BigDecimal("15000.00"));
        // Set the supplier for the product
        product.setSupplier(supplier);
        // Mock the repository to return the product
        when(repo.findById(1L)).thenReturn(Optional.of(product));

        // Act: call the service method and verify the response
        ProductResponse response = service.findById(1L);

        // Assert: verify the response matches the expected values
        assertThat(response.name()).isEqualTo("Gula Pasir");
        // Verify the supplier name is correctly set in the response
        assertThat(response.supplierName()).isEqualTo("PT SUmber Pangan");
    }

    @Test
    // Verifies that findById throws ProductNotFoundException when the product is not found
    void findById_whenProductNotFound_throwsException(){
        // Arrange: mock the repository to return an empty Optional
        // Act: call the service method and verify it throws ProductNotFoundException
        when(repo.findById(999L)).thenReturn(Optional.empty());
        // Assert: verify the exception is thrown
        assertThrows(ProductNotFoundException.class, () -> service.findById(999L));
    }

    @Test
    // Verifies that create saves the product and returns a ProductResponse when the supplier exists
    void create_WhenSupplierExists_saveAndReturnsProductResponse() {
        // Arrange: create a supplier and product request
        Supplier supplier = new Supplier("CV Argo Makmur", "Surabaya");
        // Create a product request
        ProductRequest request = new ProductRequest("Gula Pasir", 10, new BigDecimal("15000.00"), 1L);
        // Mock the supplier repository to return the supplier
        when(supplierRepo.findById(1L)).thenReturn(Optional.of(supplier));
        // Mock the product repository to return the saved product
        when(repo.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act: call the service method to create the product
        ProductResponse response = service.create(request);

        // Assert: verify the response matches the expected values
        assertThat(response.name()).isEqualTo("Gula Pasir");
        // Verify the supplier name matches the expected value
        assertThat(response.supplierName()).isEqualTo("CV Argo Makmur");
    }

    @Test
    void create_WhenProductNotFound_throwsException() {
        // Arrange: create a product request
        ProductRequest request = new ProductRequest("Gula Pasir", 10, new BigDecimal("15000.00"), 999L);

        // Mock the supplier repository to return an empty Optional
        when(supplierRepo.findById(999L)).thenReturn(Optional.empty());

        // Assert: verify the exception is thrown
        assertThrows(SupplierNotFoundException.class, () -> service.create(request));
    }

    @Test
    void findSupplierById_WhenSupplierNotFound_throwsException() {
        // Arrange: mock the supplier repository to return an empty Optional
        when(supplierRepo.findById(999L)).thenReturn(Optional.empty());
        // Assert: verify the exception is thrown
        assertThrows(SupplierNotFoundException.class, () -> service.findSupplierById(999L));
    }
}
