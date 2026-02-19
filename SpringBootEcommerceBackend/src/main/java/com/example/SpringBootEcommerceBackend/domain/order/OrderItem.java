package com.example.SpringBootEcommerceBackend.domain.order;

import com.example.SpringBootEcommerceBackend.domain.product.Product;
import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    public Product getProduct() {
        return product;
    }
    @Column(nullable = false)
    private long quantity;

    @Column(name = "price_cents", nullable = false)
    private long priceCents;

    protected OrderItem() {}

    public OrderItem(Product product, long quantity, long priceCents) {
        this.product = product;
        this.quantity = quantity;
        this.priceCents = priceCents;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public long getQuantity() { return quantity; }
    public long getPriceCents() { return priceCents; }
}
