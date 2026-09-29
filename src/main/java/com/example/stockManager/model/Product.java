package com.example.stockManager.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.DecimalMin;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Product {


    @NotNull(message = "Supplier tidak boleh kosong") //menolak null, string kosong (""), maupun yang isinya hanya spasi (" "). Ini lebih ketat daripada @NotNull.
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier; // This field represents the supplier associated with the product. It is not annotated with JPA annotations, so it will not be persisted in the database. You may want to add appropriate annotations if you intend to establish a relationship between Product and Supplier entities.

    // -- Attributes --
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nama produk tidak boleh kosong") //menolak null, string kosong (""), maupun yang isinya hanya spasi (" "). Ini lebih ketat daripada @NotNull.
    private String name;

    @Min(value = 0, message = "Stok tidak boleh negatif") //khusus untuk tipe angka seperti int atau long.
    private int stock;

    @Column (precision = 12, scale = 2)
    @DecimalMin(value = "0.0", message = "Harga tidak boleh negatif") //dipakai untuk BigDecimal karena @Min tidak mengenali tipe itu, dan nilainya ditulis sebagai String ("0.0"), bukan angka biasa.
    private BigDecimal price;

    // -- Constructor --
    protected Product() { }

    // Constructor to create a new Product with a name, stock, and price
    public Product(String name, int stock, BigDecimal price) {
        this.name = name;
        this.stock = stock;
        this.price = price;
    }

    //-- Getter --
    public Long getId() { return id; }
    public String getName() { return name;}
    public int getStock() { return stock; }
    public BigDecimal getPrice() { return price; }
    public Supplier getSupplier() { return supplier; }

    //-- Setter --
    public void setName(String name) { this.name = name;}
    public void setStock(int stock) { this.stock = stock; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setSupplier(Supplier supplier) { this.supplier = supplier; }
}
