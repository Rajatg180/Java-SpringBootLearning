package com.example.SpringEcommerceBackend.repository;

import com.example.SpringEcommerceBackend.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
}