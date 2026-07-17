package com.aimarketplace.mapper;

import com.aimarketplace.dto.response.ReviewResponse;
import com.aimarketplace.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {


    @Mapping(
            source = "user.fullName",
            target = "reviewerName"
    )
    ReviewResponse toResponse(Review review);

}