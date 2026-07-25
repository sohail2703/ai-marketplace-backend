package com.aimarketplace.mapper;

import com.aimarketplace.dto.response.ToolResponse;
import com.aimarketplace.entity.Tool;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ToolMapper {

    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "creator.fullName", target = "creatorName")
    @Mapping(source = "status", target = "status")
    ToolResponse toResponse(Tool tool);

}