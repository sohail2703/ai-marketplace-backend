package com.aimarketplace.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatResponse {

    private Long sessionId;
    private String userMessage;
    private String aiResponse;
}