package com.example.SpringEcommerceBackend.controller;

import com.example.SpringEcommerceBackend.dto.CategoryRequest;
import com.example.SpringEcommerceBackend.dto.CategoryResponse;
import com.example.SpringEcommerceBackend.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // ADMIN only
    // preauthorize is used to restrict access to this endpoint to users with the ADMIN role
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/admin/categories")
    public CategoryResponse create(@Valid @RequestBody CategoryRequest request) {
        return categoryService.create(request);
    }

    // Public
    @GetMapping("/api/categories")
    public List<CategoryResponse> getAll() {
        return categoryService.getAll();
    }

    // ADMIN only
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/api/admin/categories/{id}")
    public CategoryResponse update(
            @PathVariable UUID id,
            @Valid @RequestBody CategoryRequest request) {
        return categoryService.update(id, request);
    }

    // ADMIN only
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/api/admin/categories/{id}")
    public void delete(@PathVariable UUID id) {
        categoryService.delete(id);
    }
}
