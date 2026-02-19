package com.example.SpringBootEcommerceBackend.api.payments;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.SpringBootEcommerceBackend.application.payments.PaymentService;

@RestController
@RequestMapping("/api/orders")
public class PaymentController {

    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    record PayRequest(String idempotencyKey, boolean shouldSucceed) {
    }

    @PostMapping("/{orderId}/pay")
    public PaymentService.PaymentResult pay(@PathVariable Long orderId, @RequestBody PayRequest req) {

        if (req.idempotencyKey() == null || req.idempotencyKey().isBlank()) {
            throw new IllegalArgumentException("idempotencyKey is required");
        }
        return paymentService.pay(orderId, req.idempotencyKey(), req.shouldSucceed());
    }
}
