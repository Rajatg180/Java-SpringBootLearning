package com.example.SpringEcommerceBackend.service.impl;

import com.example.SpringEcommerceBackend.dto.ProductRequest;
import com.example.SpringEcommerceBackend.dto.ProductResponse;
import com.example.SpringEcommerceBackend.entity.Category;
import com.example.SpringEcommerceBackend.entity.Product;
import com.example.SpringEcommerceBackend.exception.ResourceNotFoundException;
import com.example.SpringEcommerceBackend.repository.CategoryRepository;
import com.example.SpringEcommerceBackend.repository.ProductRepository;
import com.example.SpringEcommerceBackend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    // 🔹 CREATE PRODUCT (ADMIN only)
    @Override
    public ProductResponse create(ProductRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found"));

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .category(category)
                .build();

        Product saved = productRepository.save(product);

        return mapToResponse(saved);
    }

    // 🔹 GET ALL WITH PAGINATION + SORTING
    @Override
    public Page<ProductResponse> getAll(int page, int size, String sortBy, String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return productRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    // 🔹 SEARCH BY NAME
    @Override
    public Page<ProductResponse> searchByName(String name, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return productRepository
                .findByNameContainingIgnoreCase(name, pageable)
                .map(this::mapToResponse);
    }

    // 🔹 FILTER BY PRICE RANGE
    @Override
    public Page<ProductResponse> filterByPrice(
            BigDecimal min,
            BigDecimal max,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);

        return productRepository
                .findByPriceBetween(min, max, pageable)
                .map(this::mapToResponse);
    }

    // 🔹 FILTER BY CATEGORY
    @Override
    public Page<ProductResponse> filterByCategory(
            UUID categoryId,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);

        return productRepository
                .findByCategoryId(categoryId, pageable)
                .map(this::mapToResponse);
    }

    // 🔹 PRIVATE MAPPER
    private ProductResponse mapToResponse(Product product) {

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getName())
                .build();
    }
}
