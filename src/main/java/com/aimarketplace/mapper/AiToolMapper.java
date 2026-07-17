package com.aimarketplace.mapper;

import com.aimarketplace.dto.response.AiToolResponse;
import com.aimarketplace.entity.AiTool;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AiToolMapper {


    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "provider.fullName", target = "providerName")
    AiToolResponse toResponse(AiTool aiTool);

}