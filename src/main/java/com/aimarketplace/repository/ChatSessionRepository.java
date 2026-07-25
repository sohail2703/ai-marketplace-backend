package com.aimarketplace.repository;

import com.aimarketplace.entity.ChatSession;
import com.aimarketplace.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatSessionRepository extends JpaRepository<ChatSession, Long> {

    Page<ChatSession> findByUser(User user, Pageable pageable);
}