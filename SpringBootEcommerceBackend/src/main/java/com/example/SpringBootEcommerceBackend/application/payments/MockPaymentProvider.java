package com.example.SpringBootEcommerceBackend.application.payments;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MockPaymentProvider {

    public PaymentResult charge(long amountCents, boolean shouldSucceed) {
        // in real life: call Razorpay/Stripe/PayPal etc
        // here: deterministic success/fail for testing
        if (shouldSucceed) {
            return PaymentResult.success("MOCK-" + UUID.randomUUID());
        }
        return PaymentResult.failed("MOCK-FAIL-" + UUID.randomUUID());
    }

    public record PaymentResult(boolean success, String providerRef) {
        public static PaymentResult success(String ref) { return new PaymentResult(true, ref); }
        public static PaymentResult failed(String ref) { return new PaymentResult(false, ref); }
    }
}
