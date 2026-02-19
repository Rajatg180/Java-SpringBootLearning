package com.example.SpringBootEcommerceBackend.infrastructure.repo;
import com.example.SpringBootEcommerceBackend.domain.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import com.example.SpringBootEcommerceBackend.domain.payment.PaymentStatus;

public interface PaymentRepository extends JpaRepository<Payment,Long> {

    // flow -> check if there is already a payment with the same order id and idempotency key -> if yes, return it, if no, create a new payment record with PENDING status -> call the payment provider -> if success, update the payment record to SUCCESS, if failed, update it to FAILED. This way we can ensure that even if the client retries the payment request due to network issues or other reasons, we won't create duplicate payments in our system.

    // this will be used to check if a payment with the same order id and idempotency key already exists, to prevent duplicate payments in case of retries
    Optional<Payment> findByOrderIdAndIdempotencyKey(Long orderId, String idempotencyKey);

    // this will be used to check if there is already a successful payment for the same order, to prevent multiple successful payments for the same order
    boolean existsByOrderIdAndStatus(Long orderId, PaymentStatus status); 
}  
