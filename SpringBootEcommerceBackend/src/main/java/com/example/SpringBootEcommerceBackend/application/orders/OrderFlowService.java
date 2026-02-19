package com.example.SpringBootEcommerceBackend.application.orders;

import com.example.SpringBootEcommerceBackend.domain.inventory.Inventory;
import com.example.SpringBootEcommerceBackend.infrastructure.repo.InventoryRepository;
import com.example.SpringBootEcommerceBackend.infrastructure.repo.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderFlowService {

    private final OrderRepository orders;
    private final InventoryRepository inventory;

    public OrderFlowService(OrderRepository orders, InventoryRepository inventory) {
        this.orders = orders;
        this.inventory = inventory;
    }


    // transactional annotion is used to ensure that all the operations within the method are executed within a single transaction. If any operation fails, the entire transaction will be rolled back, ensuring data consistency.
    // it used AOP (Aspect oritented programing ) whihc uses @Around adivce to begin transaction , rollback transactian and commit transaction 
    @Transactional
    public void ship(Long orderId) {
        var order = orders.lockById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        order.markShipped();
        // nothing else needed (we already decreased stock at order creation time)
    }

    @Transactional
    public void cancel(Long orderId) {
        var order = orders.lockById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        // If it becomes CANCELLED now, we must restore inventory
        var before = order.getStatus();
        order.cancel();

        // If it was already cancelled, do nothing (idempotent)
        if (before.name().equals("CANCELLED")) return;

        // restore stock for each item (lock each inventory row)
        for (var item : order.getItems()) {
            var productId = item.getProduct().getId();
            Inventory inv = inventory.lockByProductId(productId)
                    .orElseThrow(() -> new IllegalStateException("Inventory not found for product " + productId));

            inv.increase(item.getQuantity());
        }
    }
}
