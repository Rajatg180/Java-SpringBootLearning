package com.example.SpringEcommerceBackend.service;

import com.example.SpringEcommerceBackend.dto.CategoryRequest;
import com.example.SpringEcommerceBackend.dto.CategoryResponse;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    CategoryResponse create(CategoryRequest request);

    List<CategoryResponse> getAll();

    CategoryResponse update(UUID id, CategoryRequest request);

    void delete(UUID id);
}
