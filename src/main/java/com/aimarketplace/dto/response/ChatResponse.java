package com.aimarketplace.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatResponse {

    private String userMessage;

    private String aiResponse;

    private String suggestedTool;

}