package com.aimarketplace.mapper;

import com.aimarketplace.dto.response.CategoryResponse;
import com.aimarketplace.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponse toResponse(Category category);

}