package com.example.SpringBootEcommerceBackend.application.orders;

import com.example.SpringBootEcommerceBackend.domain.inventory.Inventory;
import com.example.SpringBootEcommerceBackend.domain.order.*;
import com.example.SpringBootEcommerceBackend.domain.product.Product;
import com.example.SpringBootEcommerceBackend.domain.user.User;
import com.example.SpringBootEcommerceBackend.infrastructure.repo.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    private final UserRepository users;
    private final ProductRepository products;
    private final InventoryRepository inventoryRepo;
    private final OrderRepository orders;

    public OrderService(UserRepository users,
            ProductRepository products,
            InventoryRepository inventoryRepo,
            OrderRepository orders) {
        this.users = users;
        this.products = products;
        this.inventoryRepo = inventoryRepo;
        this.orders = orders;
    }

    @Transactional
    public Order createOrder(Long userId, List<OrderRequestItem> requestItems) {

        User user = users.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Order order = new Order(user);

        // now for each order item in the request, we need to check if the product
        // exists, if the inventory is sufficient, and then create an OrderItem and add
        // it to the order
        for (OrderRequestItem req : requestItems) {
            Product product = products.findById(req.productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));

            Inventory inventory = inventoryRepo.lockByProductId(product.getId()).orElseThrow(
                    () -> new IllegalArgumentException("Inventory not found for product id: " + product.getId()));

            if (inventory.getAvailableQty() < req.quantity) {
                throw new IllegalArgumentException("Insufficient stock for product id: " + product.getId());
            }

            inventory.decrease(req.quantity);

            OrderItem item = new OrderItem(
                    product,
                    req.quantity(),
                    product.getPriceCents());

            order.addItem(item);
        }
        // at the end of the method, if no exception is thrown, we can save the order and return it
        return orders.save(order);
    }

    public record OrderRequestItem(Long productId, long quantity) {
    }
}


/*

@Transactional ensures:

If stock is insufficient → exception → rollback
If DB fails → rollback
If anything crashes mid-loop → rollback

*/