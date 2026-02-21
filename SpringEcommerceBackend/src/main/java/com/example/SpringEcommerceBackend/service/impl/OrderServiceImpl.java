package com.example.SpringEcommerceBackend.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.SpringEcommerceBackend.dto.OrderItemResponse;
import com.example.SpringEcommerceBackend.dto.OrderResponse;
import com.example.SpringEcommerceBackend.dto.UpdateOrderStatusRequest;
import com.example.SpringEcommerceBackend.entity.Cart;
import com.example.SpringEcommerceBackend.entity.CartItem;
import com.example.SpringEcommerceBackend.entity.Order;
import com.example.SpringEcommerceBackend.entity.OrderItem;
import com.example.SpringEcommerceBackend.entity.Product;
import com.example.SpringEcommerceBackend.entity.User;
import com.example.SpringEcommerceBackend.enums.OrderStatus;
import com.example.SpringEcommerceBackend.exception.CartEmptyException;
import com.example.SpringEcommerceBackend.exception.InsufficientStockException;
import com.example.SpringEcommerceBackend.exception.ResourceNotFoundException;
import com.example.SpringEcommerceBackend.repository.CartRepository;
import com.example.SpringEcommerceBackend.repository.OrderItemRepository;
import com.example.SpringEcommerceBackend.repository.OrderRepository;
import com.example.SpringEcommerceBackend.repository.ProductRepository;
import com.example.SpringEcommerceBackend.repository.UserRepository;
import com.example.SpringEcommerceBackend.service.OrderService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

        private final OrderRepository orderRepository;
        private final OrderItemRepository orderItemRepository;
        private final CartRepository cartRepository;
        private final ProductRepository productRepository;
        private final UserRepository userRepository;

        @Override
        @Transactional
        public OrderResponse placeOrder() {
                User user = getCurrentUser();

                Cart cart = cartRepository.findByUser(user)
                                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

                if (cart.getItems().isEmpty()) {
                        throw new CartEmptyException("Cart is empty");
                }

                BigDecimal total = BigDecimal.ZERO;

                List<OrderItem> orderItems = new ArrayList<>();

                for (CartItem cartItem : cart.getItems()) {
                        Product product = cartItem.getProduct();

                        if (product.getStockQuantity() < cartItem.getQuantity()) {
                                throw new InsufficientStockException(
                                                "Insufficient stock for product: " + product.getName());
                        }

                        product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());
                        productRepository.save(product);
                        BigDecimal itemTotal = product.getPrice()
                                        .multiply(BigDecimal.valueOf(cartItem.getQuantity()));
                        total = total.add(itemTotal);

                        OrderItem orderItem = OrderItem.builder()
                                        .product(product)
                                        .quantity(cartItem.getQuantity())
                                        .priceAtPurchase(product.getPrice())
                                        .build();

                        orderItems.add(orderItem);
                }

                Order order = Order.builder()
                                .user(user)
                                .totalPrice(total)
                                .status(OrderStatus.PENDING)
                                .items(new ArrayList<>())
                                .build();

                Order savedOrder = orderRepository.save(order);

                for (OrderItem item : orderItems) {
                        item.setOrder(savedOrder);
                        orderItemRepository.save(item);
                }
                cart.getItems().clear();
                cartRepository.save(cart);

                return mapToResponse(savedOrder, orderItems);
        }

        @Override
        public List<OrderResponse> getMyOrders() {
                User user = getCurrentUser();

                return orderRepository.findByUser(user)
                                .stream()
                                .map(order -> mapToResponse(order, order.getItems()))
                                .toList();
        }

        @Override
        public List<OrderResponse> getAllOrders() {
                return orderRepository.findAll()
                                .stream()
                                .map(order -> mapToResponse(order, order.getItems()))
                                .toList();
        }

        @Override
        public OrderResponse updateStatus(UUID orderId, UpdateOrderStatusRequest request) {

                Order order = orderRepository.findById(orderId)
                                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

                order.setStatus(request.getStatus());
                Order updated = orderRepository.save(order);

                return mapToResponse(updated, updated.getItems());
        }

        private User getCurrentUser() {

                String email = SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                .getName();

                return userRepository.findByEmail(email)
                                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        }

        private OrderResponse mapToResponse(
                        Order order,
                        List<OrderItem> items) {

                List<OrderItemResponse> itemResponses = items.stream()
                                .map(item -> OrderItemResponse.builder()
                                                .productId(item.getProduct().getId())
                                                .productName(item.getProduct().getName())
                                                .quantity(item.getQuantity())
                                                .priceAtPurchase(item.getPriceAtPurchase())
                                                .build())
                                .toList();

                return OrderResponse.builder()
                                .orderId(order.getId())
                                .totalPrice(order.getTotalPrice())
                                .status(order.getStatus())
                                .items(itemResponses)
                                .build();
        }

}
