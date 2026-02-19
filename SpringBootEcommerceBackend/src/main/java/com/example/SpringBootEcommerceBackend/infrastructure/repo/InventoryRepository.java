package com.example.SpringBootEcommerceBackend.infrastructure.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.SpringBootEcommerceBackend.domain.inventory.Inventory;

import jakarta.persistence.LockModeType;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE) // this will lock the inventory record for update, preventing other transactions from modifying it until the current transaction is complete
    @Query("select i from Inventory i where i.product.id = :productId") // this is a JPQL query that will find the inventory record by product id 
    //  this is necessary because we want to lock the inventory record when we are trying to decrease the stock , to prevent the race condition where two transactions are trying to decrease the stock at the same time and end up with negative stock
    Optional<Inventory> lockByProductId(@Param("productId") Long productId);

} 
