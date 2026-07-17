package com.aimarketplace.mapper;

import com.aimarketplace.dto.response.ChatResponse;
import com.aimarketplace.entity.ChatMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatMapper {

    ChatResponse toResponse(
            ChatMessage chatMessage
    );

}