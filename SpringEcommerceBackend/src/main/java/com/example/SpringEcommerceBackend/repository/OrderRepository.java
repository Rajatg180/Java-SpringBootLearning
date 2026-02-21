package com.example.SpringEcommerceBackend.repository;

import com.example.SpringEcommerceBackend.entity.Order;
import com.example.SpringEcommerceBackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findByUser(User user);
}
