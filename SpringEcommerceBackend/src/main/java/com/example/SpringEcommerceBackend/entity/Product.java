package com.example.SpringEcommerceBackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stockQuantity;

    // Many products belong to one category
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false) // create category_id column in products table which is a foreign key to categories table
    private Category category;
}
