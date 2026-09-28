package com.example.stockManager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity 
public class Supplier {
    // -- Attributes --
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    // -- Attributes --
    private String name;
    private String city;
    
    // -- Constructor --
    protected Supplier() { }

    // Constructor to create a new Supplier with a name and city
    public Supplier(String name, String city) {
        this.name = name;
        this.city = city;
    }

    //-- Getter --
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getCity() { return city; }

    //-- Setter --
    public void setName(String name) { this.name = name; }
    public void setCity(String city) { this.city = city; }
}
