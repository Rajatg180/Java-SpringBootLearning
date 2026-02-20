package com.example.SpringEcommerceBackend.controller;

import com.example.SpringEcommerceBackend.dto.ProductRequest;
import com.example.SpringEcommerceBackend.dto.ProductResponse;
import com.example.SpringEcommerceBackend.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 🔹 ADMIN ONLY
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/admin/products")
    public ProductResponse create(@Valid @RequestBody ProductRequest request) {
        return productService.create(request);
    }

    // 🔹 PUBLIC — Pagination + Sorting
    @GetMapping("/api/products")
    public Page<ProductResponse> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return productService.getAll(page, size, sortBy, direction);
    }

    // 🔹 SEARCH
    @GetMapping("/api/products/search")
    public Page<ProductResponse> search(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return productService.searchByName(name, page, size);
    }

    // 🔹 FILTER PRICE
    @GetMapping("/api/products/filter-price")
    public Page<ProductResponse> filterByPrice(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return productService.filterByPrice(min, max, page, size);
    }

    // 🔹 FILTER CATEGORY
    @GetMapping("/api/products/filter-category")
    public Page<ProductResponse> filterByCategory(
            @RequestParam UUID categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return productService.filterByCategory(categoryId, page, size);
    }
}