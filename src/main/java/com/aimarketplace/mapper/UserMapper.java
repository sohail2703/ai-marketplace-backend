package com.aimarketplace.mapper;

import com.aimarketplace.dto.response.UserResponse;
import com.aimarketplace.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponse(User user);

}