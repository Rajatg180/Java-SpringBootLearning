package com.example.SpringEcommerceBackend.service;

import com.example.SpringEcommerceBackend.dto.AddToCartRequest;
import com.example.SpringEcommerceBackend.dto.CartResponse;

import java.util.UUID;

public interface CartService {

    void addToCart(AddToCartRequest request);

    CartResponse viewCart();

    void removeItem(UUID productId);
}