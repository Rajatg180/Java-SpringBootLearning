package com.example.SpringBootEcommerceBackend.domain.payment;

import com.example.SpringBootEcommerceBackend.domain.order.Order;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // we have used @ManyToOne here instead of @OneToOne because we want to allow multiple payment attempts for the same order (e.g. if the first attempt fails, the user can try again). The idempotency key will ensure that only one successful payment is recorded for the same order and key combination.
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Column(name = "amount_cents", nullable = false)
    private long amountCents;

    @Column(name = "idempotency_key", nullable = false)
    private String idempotencyKey;

    @Column(name = "provider_ref")
    private String providerRef;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    protected Payment() {}

    public Payment(Order order, long amountCents, String idempotencyKey) {
        this.order = order;
        this.amountCents = amountCents;
        this.idempotencyKey = idempotencyKey;
        this.status = PaymentStatus.PENDING;
    }

    public void markSuccess(String providerRef) {
        this.status = PaymentStatus.SUCCESS;
        this.providerRef = providerRef;
    }

    public void markFailed(String providerRef) {
        this.status = PaymentStatus.FAILED;
        this.providerRef = providerRef;
    }

    public PaymentStatus getStatus() { return status; }
    public String getProviderRef() { return providerRef; }
}
