package com.example.SpringEcommerceBackend.controller;

import com.example.SpringEcommerceBackend.dto.OrderResponse;
import com.example.SpringEcommerceBackend.dto.UpdateOrderStatusRequest;
import com.example.SpringEcommerceBackend.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // Customer places order
    @PostMapping
    public OrderResponse placeOrder() {
        return orderService.placeOrder();
    }

    // Customer views own orders
    @GetMapping("/my")
    public List<OrderResponse> getMyOrders() {
        return orderService.getMyOrders();
    }

    // ADMIN only
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{orderId}/status")
    public OrderResponse updateOrderStatus(
            @PathVariable UUID orderId,
            @Valid @RequestBody UpdateOrderStatusRequest request) {
        return orderService.updateStatus(orderId, request);
    }
}