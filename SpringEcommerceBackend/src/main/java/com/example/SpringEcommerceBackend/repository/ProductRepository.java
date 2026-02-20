package com.example.SpringEcommerceBackend.repository;

import com.example.SpringEcommerceBackend.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    // Pagination + search
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // Filter by price range
    Page<Product> findByPriceBetween(BigDecimal min, BigDecimal max, Pageable pageable);

    // Filter by category
    Page<Product> findByCategoryId(UUID categoryId, Pageable pageable);
}
