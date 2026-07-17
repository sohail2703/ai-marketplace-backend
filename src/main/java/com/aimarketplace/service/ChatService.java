package com.aimarketplace.service;

import com.aimarketplace.dto.request.ChatRequest;
import com.aimarketplace.dto.response.ChatResponse;
import com.aimarketplace.entity.ChatMessage;
import com.aimarketplace.entity.User;
import com.aimarketplace.mapper.ChatMapper;
import com.aimarketplace.repository.ChatMessageRepository;
import com.aimarketplace.repository.UserRepository;
import com.aimarketplace.util.PromptTemplateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ChatService {


    private final ChatClient chatClient;

    private final ChatMessageRepository chatMessageRepository;

    private final UserRepository userRepository;

    private final ChatMapper chatMapper;

    private final PromptTemplateUtil promptTemplateUtil;



    public ChatResponse chat(ChatRequest request){


        User user = getCurrentUser();


        String prompt =
                promptTemplateUtil
                        .buildAiToolAdvisorPrompt(
                                request.getMessage()
                        );


        String response =
                chatClient.prompt()
                        .user(prompt)
                        .call()
                        .content();



        ChatMessage message =
                ChatMessage.builder()
                        .userMessage(request.getMessage())
                        .aiResponse(response)
                        .user(user)
                        .build();


        return chatMapper.toResponse(
                chatMessageRepository.save(message)
        );

    }



    private User getCurrentUser(){

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();


        return userRepository.findByEmail(email)
                .orElseThrow();

    }

}