package com.aimarketplace.service;

import com.aimarketplace.dto.request.CategoryRequest;
import com.aimarketplace.dto.response.CategoryResponse;
import com.aimarketplace.entity.Category;
import com.aimarketplace.exception.ResourceNotFoundException;
import com.aimarketplace.mapper.CategoryMapper;
import com.aimarketplace.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {


    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;



    public CategoryResponse create(CategoryRequest request) {


        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();


        return categoryMapper.toResponse(
                categoryRepository.save(category)
        );

    }



    public List<CategoryResponse> getAll() {


        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();

    }



    public CategoryResponse getById(Long id) {


        Category category =
                categoryRepository.findById(id)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Category not found"
                                )
                        );


        return categoryMapper.toResponse(category);

    }

}