package com.example.stockManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.stockManager.model.Product;
import java.util.List;

// <Product, Long> adalah generics
public interface ProductRepository extends JpaRepository<Product, Long> {
    /*
    - Tipe pertama, Product: entity yang dikelola repository ini, atau tabel mana yang diurus.
    
    - Tipe kedua, Long: tipe data dari field @Id di entity tersebut, yaitu primary key-nya. Ini bukan relasi ke tabel lain, hanya tipe datanya.
    */
    List<Product> findByNameContainingIgnoreCase(String keyword);
}
