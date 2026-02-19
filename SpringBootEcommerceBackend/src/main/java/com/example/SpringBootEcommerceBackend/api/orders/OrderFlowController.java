package com.example.SpringBootEcommerceBackend.api.orders;

import com.example.SpringBootEcommerceBackend.application.orders.OrderFlowService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderFlowController {

    private final OrderFlowService flow;

    public OrderFlowController(OrderFlowService flow) {
        this.flow = flow;
    }

    @PostMapping("/{orderId}/ship")
    public void ship(@PathVariable Long orderId) {
        flow.ship(orderId);
    }

    @PostMapping("/{orderId}/cancel")
    public void cancel(@PathVariable Long orderId) {
        flow.cancel(orderId);
    }
}
