package com.aimarketplace.controller;

import com.aimarketplace.dto.request.ChatRequest;
import com.aimarketplace.dto.response.ApiResponse;
import com.aimarketplace.dto.response.ChatResponse;
import com.aimarketplace.entity.ChatSession;
import com.aimarketplace.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/sessions")
    public ApiResponse<Long> createSession(
            @RequestParam String title,
            Authentication authentication) {

        ChatSession session =
                chatService.createSession(
                        authentication.getName(),
                        title
                );

        return ApiResponse.<Long>builder()
                .success(true)
                .message("Chat session created successfully")
                .data(session.getId())
                .build();
    }

    @PostMapping("/messages")
    public ApiResponse<ChatResponse> chat(
            @Valid @RequestBody ChatRequest request,
            Authentication authentication) {

        return ApiResponse.<ChatResponse>builder()
                .success(true)
                .message("AI response generated successfully")
                .data(
                        chatService.chat(
                                request,
                                authentication.getName()
                        )
                )
                .build();
    }
}