package com.aimarketplace.controller;

import com.aimarketplace.dto.request.CategoryRequest;
import com.aimarketplace.dto.response.ApiResponse;
import com.aimarketplace.dto.response.CategoryResponse;
import com.aimarketplace.dto.response.PageResponse;
import com.aimarketplace.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<CategoryResponse> createCategory(
            @Valid @RequestBody CategoryRequest request) {

        return ApiResponse.<CategoryResponse>builder()
                .success(true)
                .message("Category created successfully")
                .data(categoryService.createCategory(request))
                .build();
    }

    @GetMapping
    public ApiResponse<PageResponse<CategoryResponse>>
    getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (page < 0 || size < 1 || size > 100) {
            throw new IllegalArgumentException(
                    "Invalid pagination parameters"
            );
        }

        return ApiResponse.<PageResponse<CategoryResponse>>builder()
                .success(true)
                .message("Categories fetched successfully")
                .data(
                        categoryService.getAllCategories(
                                page, size
                        )
                )
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<CategoryResponse> getCategory(
            @PathVariable Long id) {

        return ApiResponse.<CategoryResponse>builder()
                .success(true)
                .message("Category fetched successfully")
                .data(categoryService.getCategory(id))
                .build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<CategoryResponse> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request) {

        return ApiResponse.<CategoryResponse>builder()
                .success(true)
                .message("Category updated successfully")
                .data(
                        categoryService.updateCategory(
                                id, request
                        )
                )
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> deleteCategory(
            @PathVariable Long id) {

        categoryService.deleteCategory(id);

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Category deleted successfully")
                .data(null)
                .build();
    }
}