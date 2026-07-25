package com.aimarketplace.service;

import com.aimarketplace.dto.request.ChatRequest;
import com.aimarketplace.dto.response.ChatResponse;
import com.aimarketplace.entity.ChatMessage;
import com.aimarketplace.entity.ChatSession;
import com.aimarketplace.entity.User;
import com.aimarketplace.enums.MessageRole;
import com.aimarketplace.exception.ResourceNotFoundException;
import com.aimarketplace.repository.ChatMessageRepository;
import com.aimarketplace.repository.ChatSessionRepository;
import com.aimarketplace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatClient chatClient;
    private final ChatSessionRepository chatSessionRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;

    @Transactional
    public ChatResponse chat(
            ChatRequest request,
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));

        ChatSession session =
                chatSessionRepository
                        .findByIdAndUserId(
                                request.getSessionId(),
                                user.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Chat session not found"
                                ));

        List<ChatMessage> previousMessages =
                chatMessageRepository
                        .findByChatSessionIdOrderByCreatedAtAsc(
                                session.getId()
                        );

        String conversationContext =
                buildConversationContext(previousMessages);

        ChatMessage userMessage =
                ChatMessage.builder()
                        .chatSession(session)
                        .role(MessageRole.USER)
                        .message(request.getMessage())
                        .build();

        chatMessageRepository.save(userMessage);

        String prompt = """
                You are an AI Tool Advisor inside an AI Marketplace.

                Your responsibilities:
                - Understand the user's requirements.
                - Recommend suitable AI tools.
                - Explain why a tool fits the user's needs.
                - Consider the user's skill level.
                - Give practical and concise recommendations.

                Previous conversation:
                %s

                Current user message:
                %s
                """.formatted(
                conversationContext,
                request.getMessage()
        );

        String aiResponse =
                chatClient.prompt()
                        .user(prompt)
                        .call()
                        .content();

        ChatMessage assistantMessage =
                ChatMessage.builder()
                        .chatSession(session)
                        .role(MessageRole.ASSISTANT)
                        .message(aiResponse)
                        .build();

        chatMessageRepository.save(assistantMessage);

        return ChatResponse.builder()
                .sessionId(session.getId())
                .userMessage(request.getMessage())
                .aiResponse(aiResponse)
                .build();
    }

    private String buildConversationContext(
            List<ChatMessage> messages) {

        if (messages.isEmpty()) {
            return "No previous conversation.";
        }

        StringBuilder context =
                new StringBuilder();

        for (ChatMessage message : messages) {

            context.append(
                    message.getRole()
                            .name()
            );

            context.append(": ");

            context.append(
                    message.getMessage()
            );

            context.append("\n");
        }

        return context.toString();
    }

    @Transactional
    public ChatSession createSession(
            String email,
            String title) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));

        ChatSession session =
                ChatSession.builder()
                        .user(user)
                        .title(title)
                        .build();

        return chatSessionRepository.save(session);
    }
}