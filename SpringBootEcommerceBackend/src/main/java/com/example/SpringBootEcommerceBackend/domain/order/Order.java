package com.example.SpringBootEcommerceBackend.domain.order;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.example.SpringBootEcommerceBackend.domain.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders") // order is a reserved keyword in SQL, so we need to specify the table name
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(name = "total_amount_cents", nullable = false)
    private long totalAmountCents;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    // mappedBy = "order" means that the OrderItem entity has a field named "order" that owns the relationship
    // cascade = CascadeType.ALL means that any operation ( persist, merge, remove) performed on the Order entity will be cascaded to the associated OrderItem entities. 
    // orphanRemoval = true means that if an OrderItem is removed from the items list, it will be deleted from the database as well.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();



   protected Order() {}

    public Order(User user) {
        this.user = user;
        this.status = OrderStatus.CREATED;
    }

    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
        totalAmountCents += item.getPriceCents() * item.getQuantity();
    }

    public Long getId() { return id; }
    public OrderStatus getStatus() { return status; }
    public long getTotalAmountCents() { return totalAmountCents; }


    public void markPaid() {
        if (status != OrderStatus.CREATED) {
            throw new IllegalStateException("Order must be CREATED to be paid");
        }
        status = OrderStatus.PAID;
    }

    public void markShipped() {
        if (status != OrderStatus.PAID) {
            throw new IllegalStateException("Order must be PAID to be shipped");
        }
        status = OrderStatus.SHIPPED;
    }

    public void cancel() {
        if (status == OrderStatus.SHIPPED) {
            throw new IllegalStateException("Cannot cancel shipped order");
        }
        if (status == OrderStatus.CANCELLED) {
            return; // idempotent cancel
        }
        status = OrderStatus.CANCELLED;
    }

    public List<OrderItem> getItems() {
        return items;
    }

}
