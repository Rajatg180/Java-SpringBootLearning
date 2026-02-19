package com.example.SpringBootEcommerceBackend.application.payments;
import org.springframework.stereotype.Service;

import com.example.SpringBootEcommerceBackend.domain.order.Order;
import com.example.SpringBootEcommerceBackend.domain.order.OrderStatus;
import com.example.SpringBootEcommerceBackend.infrastructure.repo.OrderRepository;
import com.example.SpringBootEcommerceBackend.infrastructure.repo.PaymentRepository;
import com.example.SpringBootEcommerceBackend.domain.payment.PaymentStatus;
import jakarta.transaction.Transactional;
import com.example.SpringBootEcommerceBackend.domain.payment.Payment;

@Service
public class PaymentService {

    private final OrderRepository orders;
    private final PaymentRepository payments;
    private final MockPaymentProvider paymentProvider;

    public PaymentService(OrderRepository orders, PaymentRepository payments, MockPaymentProvider paymentProvider) {
        this.orders = orders;
        this.payments = payments;
        this.paymentProvider = paymentProvider;
    }


    public record PaymentResult(Long orderId, String orderStatus, String paymentStatus, String providerRef) {}


    @Transactional
    public PaymentResult pay(Long orderId,String idempotencyKey, boolean shouldSucceed) { 

        // 1) lock the order row(prevnets concurrent modification to same order)
        Order order = orders.lockById(orderId).orElseThrow(() -> new IllegalArgumentException("Order not found"));

        // 2 ) first we check if there is already a payment with orderid and idemotencykey
        var existing = payments.findByOrderIdAndIdempotencyKey(orderId, idempotencyKey);
        if(existing.isPresent()){
            // get() will return the payment object if it is present 
            var p = existing.get();
            return new PaymentResult(orderId,order.getStatus().name(),p.getStatus().name(),p.getProviderRef());  
        }

        // 3) if order is created can be paid
        if(order.getStatus() != OrderStatus.CREATED){
            throw new IllegalStateException("Only CREATED orders can be paid");
        }
        

        // 4) prevent double pay even with different idempotency keys
        if(payments.existsByOrderIdAndStatus(orderId,  PaymentStatus.SUCCESS)){
            throw new IllegalStateException("Order already paid");
        }


        // 5) create a payment record first
        Payment payment = payments.save(new Payment(order, order.getTotalAmountCents(), idempotencyKey));

        // 6) charge provider
        var res = paymentProvider.charge(order.getTotalAmountCents(), shouldSucceed);

        // 7) update states atomically in same transaction
        if(res.success()){
            payment.markSuccess(res.providerRef());
            order.markPaid();
        }
        else{
            payment.markFailed(res.providerRef()); 
            // order stays created
        }

        return new PaymentResult(order.getId(), order.getStatus().name(), payment.getStatus().name(), payment.getProviderRef());

    }

}


/*

Why this design is correct

If DB write fails anywhere → entire transaction rolls back.

If someone calls pay twice:

same idempotency key → returns same result

different key after success → rejected (already paid)

Lock ensures no simultaneous cancel/ship races.


*/