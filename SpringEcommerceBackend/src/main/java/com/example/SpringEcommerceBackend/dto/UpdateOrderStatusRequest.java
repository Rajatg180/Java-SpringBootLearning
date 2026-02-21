package com.example.SpringEcommerceBackend.dto;

import com.example.SpringEcommerceBackend.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateOrderStatusRequest {
    @NotNull
    private OrderStatus status;
}