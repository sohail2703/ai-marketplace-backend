package com.aimarketplace.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chat_messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage extends BaseEntity {

    @Column(nullable = false, length = 4000)
    private String userMessage;

    @Column(nullable = false, length = 10000)
    private String aiResponse;

    @Column(length = 150)
    private String suggestedTool;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}