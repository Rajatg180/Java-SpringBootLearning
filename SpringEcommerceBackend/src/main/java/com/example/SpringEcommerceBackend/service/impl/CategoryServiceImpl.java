package com.example.SpringEcommerceBackend.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.SpringEcommerceBackend.dto.CategoryRequest;
import com.example.SpringEcommerceBackend.dto.CategoryResponse;
import com.example.SpringEcommerceBackend.entity.Category;
import com.example.SpringEcommerceBackend.exception.ResourceNotFoundException;
import com.example.SpringEcommerceBackend.repository.CategoryRepository;
import com.example.SpringEcommerceBackend.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse create(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new RuntimeException("Category with name '" + request.getName() + "' already exists");
        }

        Category category = Category.builder().name(request.getName()).build();

        Category savedCategory = categoryRepository.save(category);

        return CategoryResponse.builder().id(savedCategory.getId()).name(savedCategory.getName()).build();
    }

    @Override
    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll().stream()
                .map(category -> CategoryResponse.builder().id(category.getId()).name(category.getName()).build())
                .toList();
    }

    @Override
    public CategoryResponse update(UUID id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        category.setName(request.getName());

        Category updated = categoryRepository.save(category);

        return CategoryResponse.builder()
                .id(updated.getId())
                .name(updated.getName())
                .build();
    }

    @Override
    public void delete(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        categoryRepository.delete(category);
    }

}
