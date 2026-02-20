package com.example.SpringEcommerceBackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "carts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart extends BaseEntity {

    // One cart belongs to one user
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    // One cart has many cart items
    // CascadeType.ALL ensures that when a cart is deleted, its items are also deleted
    // orphanRemoval = true ensures that if a cart item is removed from the cart, it is also deleted from the database
    // mappedBy = "cart" indicates that the CartItem entity owns the relationship and has a field named "cart" that maps back to this Cart entity
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items;
}
