package com.example.SpringEcommerceBackend.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.SpringEcommerceBackend.dto.AddToCartRequest;
import com.example.SpringEcommerceBackend.dto.CartItemResponse;
import com.example.SpringEcommerceBackend.dto.CartResponse;
import com.example.SpringEcommerceBackend.entity.Cart;
import com.example.SpringEcommerceBackend.entity.CartItem;
import com.example.SpringEcommerceBackend.entity.Product;
import com.example.SpringEcommerceBackend.entity.User;
import com.example.SpringEcommerceBackend.exception.ResourceNotFoundException;
import com.example.SpringEcommerceBackend.repository.CartItemRepository;
import com.example.SpringEcommerceBackend.repository.CartRepository;
import com.example.SpringEcommerceBackend.repository.ProductRepository;
import com.example.SpringEcommerceBackend.repository.UserRepository;
import com.example.SpringEcommerceBackend.service.CartService;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {

        // this method retrieves the currently authenticated user's email from the
        // security context and then fetches the corresponding User entity from the
        // database.
        // If the user is not found, it throws a ResourceNotFoundException.
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public void addToCart(AddToCartRequest request) {

        User user = getCurrentUser();

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (product.getStockQuantity() < request.getQuantity()) {
            throw new IllegalArgumentException("Not enough stock for product: " + product.getName());
        }

        // get or creat cart for user

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = Cart.builder()
                            .user(user)
                            .items(new ArrayList<>())
                            .build();
                    return cartRepository.save(newCart);
                });

        // check if item already in cart
        CartItem cartItem = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), product.getId())
                .orElse(null);

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
        } else {
            cartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(request.getQuantity())
                    .build();

        }

        cartItemRepository.save(cartItem);
    }

    @Override
    public CartResponse viewCart() {
        
        User user = getCurrentUser();

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));

        List<CartItemResponse> itemResponses = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItem item : cart.getItems()) {

            BigDecimal itemTotal =
                    item.getProduct().getPrice()
                            .multiply(BigDecimal.valueOf(item.getQuantity()));

            totalAmount = totalAmount.add(itemTotal);

            itemResponses.add(
                    CartItemResponse.builder()
                            .productId(item.getProduct().getId())
                            .productName(item.getProduct().getName())
                            .price(item.getProduct().getPrice())
                            .quantity(item.getQuantity())
                            .totalPrice(itemTotal)
                            .build()
            );
        }

        return CartResponse.builder()
                .items(itemResponses)
                .totalAmount(totalAmount)
                .build();

    }

    @Override
    public void removeItem(UUID productId) {

        User user = getCurrentUser();

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));

        CartItem item = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Item not found in cart"));

        cartItemRepository.delete(item);
    }

}
