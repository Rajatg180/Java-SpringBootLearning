package com.example.SpringEcommerceBackend.service;

import com.example.SpringEcommerceBackend.dto.ProductRequest;
import com.example.SpringEcommerceBackend.dto.ProductResponse;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.UUID;

public interface ProductService {

    ProductResponse create(ProductRequest request);

    Page<ProductResponse> getAll(int page, int size, String sortBy, String direction);

    Page<ProductResponse> searchByName(String name, int page, int size);

    Page<ProductResponse> filterByPrice(BigDecimal min, BigDecimal max, int page, int size);

    Page<ProductResponse> filterByCategory(UUID categoryId, int page, int size);
}