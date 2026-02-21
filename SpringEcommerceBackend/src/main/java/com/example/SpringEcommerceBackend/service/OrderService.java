package com.example.SpringEcommerceBackend.service;

import com.example.SpringEcommerceBackend.dto.OrderResponse;
import com.example.SpringEcommerceBackend.dto.UpdateOrderStatusRequest;

import java.util.List;

public interface OrderService {

    OrderResponse placeOrder();

    List<OrderResponse> getMyOrders();

    List<OrderResponse> getAllOrders(); // ADMIN only

    OrderResponse updateStatus(java.util.UUID orderId, UpdateOrderStatusRequest request);
}
