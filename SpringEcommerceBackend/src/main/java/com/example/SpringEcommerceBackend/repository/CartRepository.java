package com.example.SpringEcommerceBackend.repository;

import com.example.SpringEcommerceBackend.entity.Cart;
import com.example.SpringEcommerceBackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {

    Optional<Cart> findByUser(User user);
}
