package com.example.SpringBootEcommerceBackend.infrastructure.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.SpringBootEcommerceBackend.domain.order.Order;

import jakarta.persistence.LockModeType;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // blocking order means when we are processing a payment or cancelling an order, we want to lock the order record to prevent other transactions from modifying it at the same time (means when other requests like /pay or /cancel arrive at the same time, locking ensures only one wins and the other will wait until the lock is released, this way we can prevent race conditions and ensure data consistency in our system)

    // Because two requests like /pay or /cancel can arrive at the same time. Locking ensures only one wins.
    @Lock(LockModeType.PESSIMISTIC_WRITE) // this annotation is used to specify that the method should acquire a pessimistic write lock on the entity being queried. This means that when this method is called, it will lock the corresponding database row for writing, preventing other transactions from modifying it until the lock is released.
    @Query("select o from Order o where o.id = :id")
    Optional<Order> lockById(@Param("id") Long id);
}
