package com.example.SpringEcommerceBackend.controller;

import com.example.SpringEcommerceBackend.dto.AddToCartRequest;
import com.example.SpringEcommerceBackend.dto.CartResponse;
import com.example.SpringEcommerceBackend.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // Authenticated users only
    @PostMapping("/add")
    public void addToCart(@Valid @RequestBody AddToCartRequest request) {
        cartService.addToCart(request);
    }

    @GetMapping
    public CartResponse viewCart() {
        return cartService.viewCart();
    }

    @DeleteMapping("/remove/{productId}")
    public void removeItem(@PathVariable UUID productId) {
        cartService.removeItem(productId);
    }
}