package com.aimarketplace.service;

import com.aimarketplace.dto.request.CategoryRequest;
import com.aimarketplace.dto.response.CategoryResponse;
import com.aimarketplace.dto.response.PageResponse;
import com.aimarketplace.entity.Category;
import com.aimarketplace.exception.BadRequestException;
import com.aimarketplace.exception.ResourceNotFoundException;
import com.aimarketplace.mapper.CategoryMapper;
import com.aimarketplace.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @CacheEvict(value = "categories", allEntries = true)
    public CategoryResponse createCategory(CategoryRequest request) {

        if (categoryRepository.existsByNameIgnoreCase(request.getName())) {
            throw new BadRequestException("Category already exists");
        }

        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        return categoryMapper.toResponse(
                categoryRepository.save(category)
        );
    }

    @Cacheable(
            value = "categories",
            key = "#page + '-' + #size"
    )
    public PageResponse<CategoryResponse> getAllCategories(
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Category> categoryPage =
                categoryRepository.findAll(pageable);

        return PageResponse.<CategoryResponse>builder()
                .content(
                        categoryPage.getContent()
                                .stream()
                                .map(categoryMapper::toResponse)
                                .toList()
                )
                .page(categoryPage.getNumber())
                .size(categoryPage.getSize())
                .totalElements(categoryPage.getTotalElements())
                .totalPages(categoryPage.getTotalPages())
                .last(categoryPage.isLast())
                .build();
    }

    public CategoryResponse getCategory(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id: " + id
                        ));

        return categoryMapper.toResponse(category);
    }

    @CacheEvict(value = "categories", allEntries = true)
    public CategoryResponse updateCategory(
            Long id,
            CategoryRequest request) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id: " + id
                        ));

        if (categoryRepository.existsByNameIgnoreCaseAndIdNot(
                request.getName(), id)) {

            throw new BadRequestException(
                    "Category with this name already exists"
            );
        }

        category.setName(request.getName());
        category.setDescription(request.getDescription());

        return categoryMapper.toResponse(
                categoryRepository.save(category)
        );
    }

    @CacheEvict(value = "categories", allEntries = true)
    public void deleteCategory(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id: " + id
                        ));

        categoryRepository.delete(category);
    }
}