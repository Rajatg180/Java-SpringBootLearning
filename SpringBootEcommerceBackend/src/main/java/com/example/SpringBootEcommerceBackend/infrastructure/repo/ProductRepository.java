package com.example.SpringBootEcommerceBackend.infrastructure.repo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SpringBootEcommerceBackend.domain.product.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {
    // this will allow us to find all active products with pagination, 
    // we can use this in our service layer to get a page of active products and return it to the client
    Page<Product> findByActiveTrue(Pageable pageable);    
} 
