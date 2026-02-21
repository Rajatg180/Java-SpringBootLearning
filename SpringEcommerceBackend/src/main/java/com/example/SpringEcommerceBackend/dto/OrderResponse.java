package com.example.SpringEcommerceBackend.dto;

import com.example.SpringEcommerceBackend.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class OrderResponse {

    private UUID orderId;
    private BigDecimal totalPrice;
    private OrderStatus status;
    private List<OrderItemResponse> items;
}
