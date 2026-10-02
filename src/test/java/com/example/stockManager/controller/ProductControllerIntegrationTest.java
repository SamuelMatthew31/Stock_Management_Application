package com.example.stockManager.controller;

import org.junit.jupiter.api.Test; // for testing controller endpoints
import org.springframework.beans.factory.annotation.Autowired; // for dependency injection
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc; // for configuring MockMvc
import org.springframework.boot.test.context.SpringBootTest; // for loading the application context
import org.springframework.http.MediaType; // for specifying the media type of the request
import org.springframework.test.web.servlet.MockMvc; // for testing controller endpoints
import org.springframework.transaction.annotation.Transactional; // for rolling back transactions after each test

import com.example.stockManager.model.Product; // for testing the Product model
import com.example.stockManager.model.Supplier; // for testing the Supplier model


import com.example.stockManager.repository.SupplierRepository; // for testing the SupplierRepository
import com.example.stockManager.repository.ProductRepository; // for testing the ProductRepository

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest // for loading the application context
@AutoConfigureMockMvc // for configuring MockMvc
@Transactional // for rolling back transactions after each test

// Integration test for ProductController
class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc; // for testing controller endpoints

    @Autowired
    private SupplierRepository supplierRepo; // for testing the SupplierRepository

    @Autowired
    private ProductRepository productRepo; // for testing the ProductRepository

    @Test
    // when invalid request is sent, should return 400 Bad Request
    void createProduct_WHenInvalidRequest_return400BadRequest() throws Exception {
        // create invalid JSON request
        String invalidJson = """
            {
                "name": "",
                "stock": 10,
                "price": 15000.00,
                "supplierId": 1
            }
        """;

        // send invalid JSON request and expect 400 Bad Request
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Validasi gagal"));
    }

    @Test
    // when valid request is sent, should return 201 Created
    void createProduct_whenValidRequest_return201Created() throws Exception {
        // save a supplier to use in the valid JSON request
        Supplier supplier = supplierRepo.save(new Supplier("PT Sumber Makmur", "Jakarta"));

        // create valid JSON request
        String validJson = """
            {
                "name": "Minyak Goreng",
                "stock": 10,
                "price": 15000.00,
                "supplierId": %d
            }
            """.formatted(supplier.getId());

        // send valid JSON request and expect 201 Created
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON) // set content type to JSON
                .content(validJson)) // send the request body
                .andExpect(status().isCreated()); // expect 201 Created status
    }

    @Test
    // when supplier is not found, should return 404 Not Found
    void createProduct_whenSupplierNotFound_Returns404NotFound() throws Exception {
        // send invalid JSON request with non-existent supplierId and expect 404 Not Found
        Long nonExistentSupplierId = 999L;

        // send invalid JSON request with non-existent supplierId and expect 404 Not Found
        String requestBody = """
            {
                "name": "Kopi Susu",
                "stock": 50,
                "price": 25000.00,
                "supplierId": %d
            }
            """.formatted(nonExistentSupplierId);

        // send invalid JSON request with non-existent supplierId and expect 404 Not Found
        mockMvc.perform(post("/api/products")
                    .contentType(MediaType.APPLICATION_JSON) // set content type to JSON
                    .content(requestBody)) // send the request body
                    .andExpect(status().isNotFound()) // expect 404 Not Found status
                    .andExpect(jsonPath("$.status").value(404)) // expect status field to be 404
                    .andExpect(jsonPath("$.message").value("Supplier dengan id " + nonExistentSupplierId+ " tidak ditemukan")); // expect message field to contain the non-existent supplierId

    }

    @Test
    void getProductById_WhenProductExists_Returns200OK() throws Exception {
        Supplier supplier = supplierRepo.save(new Supplier("PT Sumber Makmur", "Jakarta"));

        // 2. Buat objek Product menggunakan konstruktor 3 parameter
        Product product = new Product("Minyak Goreng", 20, new BigDecimal("18000.00"));

        // 3. Set relasi supplier menggunakan metode setter
        product.setSupplier(supplier);

        // 4. Simpan produk ke database
        product = productRepo.save(product);

        mockMvc.perform(get("/api/products/{id}", product.getId()))
                    .andExpect(status().isOk()) // 🟢 Mengharapkan HTTP Status 200 OK
                    .andExpect(jsonPath("$.name").value("Minyak Goreng"))
                    .andExpect(jsonPath("$.supplierName").value("PT Sumber Makmur"));
    }

    @Test
    void getProductById_WhenProductDoesNotExist_Returns404NotFound() throws Exception {
        Long nonExistentProductId = 999L;

        mockMvc.perform(get("/api/products/{id}", nonExistentProductId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Produk dengan id " + nonExistentProductId + " tidak ditemukan"));
    }

    @Test
    void updateProduct_WhenProductExists_Returns200OK() throws Exception {
        // 1. Arrange: Simpan supplier dan product awal ke DB
        Supplier supplier = supplierRepo.save(new Supplier("PT Sumber Makmur", "Jakarta"));
        Product product = new Product("Minyak Goreng 1L", 10, new BigDecimal("15000.00"));
        product.setSupplier(supplier);
        product = productRepo.save(product);

        // 2. Buat JSON payload baru untuk update
        String updateJson = """
            {
                "name": "Minyak Goreng 2L",
                "stock": 25,
                "price": 28000.00,
                "supplierId": %d
            }
            """.formatted(supplier.getId());

        // 3. Act & Assert: Panggil PUT /api/products/{id}
        mockMvc.perform(put("/api/products/{id}", product.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Minyak Goreng 2L"))
                .andExpect(jsonPath("$.stock").value(25))
                .andExpect(jsonPath("$.price").value(28000.00));
    }

    @Test
    void updateProduct_WhenProductDoesNotExist_Returns404NotFound() throws Exception {
        Long nonExistentProductId = 999L;
        String updateJson = """
            {
                "name": "Minyak Goreng 2L",
                "stock": 25,
                "price": 28000.00,
                "supplierId": 1
            }
            """;

        mockMvc.perform(put("/api/products/{id}", nonExistentProductId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Produk dengan id " + nonExistentProductId + " tidak ditemukan"));
    }

    @Test
    void deleteProduct_WhenProductExists_Returns204NoContent() throws Exception {
        // 1. Arrange: Simpan supplier dan product ke DB
        Supplier supplier = supplierRepo.save(new Supplier("PT Sumber Makmur", "Jakarta"));
        Product product = new Product("Kopi Instant", 100, new BigDecimal("5000.00"));
        product.setSupplier(supplier);
        product = productRepo.save(product);

        // 2. Act & Assert: Panggil DELETE /api/products/{id}
        mockMvc.perform(delete("/api/products/{id}", product.getId()))
                .andExpect(status().isNoContent()); // Gunakan .isOk() jika controller mengembalikan HTTP 200 OK
    }

    @Test
    void deleteProduct_WhenProductDoesNotExist_Returns404NotFound() throws Exception {
        Long nonExistentProductId = 999L;

        mockMvc.perform(delete("/api/products/{id}", nonExistentProductId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Produk dengan id " + nonExistentProductId + " tidak ditemukan"));
    }
}
