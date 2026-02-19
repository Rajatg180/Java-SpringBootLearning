package com.example.SpringBootEcommerceBackend.domain.inventory;

import java.time.Instant;

import com.example.SpringBootEcommerceBackend.domain.product.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    @Column(name = "product_id")
    private Long productId;


    @OneToOne(fetch = FetchType.LAZY) // this is a one-to-one relationship with Product, and we want to load the product lazily ( only when we access it)
    @MapsId // this tell JPA that the primary key of Inventory is also a foreign key to Product
    @JoinColumn(name = "product_id") // this specify the name of the foreign key column in the inventory table
    private Product product; 

    @Column(name="available_qty", nullable = false)
    private long availableQty;

    @Column(name="updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    protected Inventory() {}

    public Inventory(Product product, long availableQty) {
        this.product = product;
        this.availableQty = availableQty;
    }

     public void decrease(long qty) {
        if (qty <= 0) throw new IllegalArgumentException("qty must be > 0");
        if (availableQty < qty) throw new IllegalStateException("insufficient stock");
        availableQty -= qty;
        updatedAt = Instant.now();
    }

    public void increase(long qty) {
        if (qty <= 0) throw new IllegalArgumentException("qty must be > 0");
        availableQty += qty;
        updatedAt = Instant.now();
    }

    public long getAvailableQty() { return availableQty; }
    public Product getProduct() { return product; }
}
