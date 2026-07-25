package com.aimarketplace.repository;

import com.aimarketplace.entity.ChatMessage;
import com.aimarketplace.entity.ChatSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByChatSessionOrderByCreatedAtAsc(ChatSession chatSession);
}