package com.aimarketplace.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ToolResponse {

    private Long id;
    private String toolName;
    private String description;
    private String websiteUrl;
    private String imageUrl;
    private String pricing;
    private String status;
    private boolean featured;
    private String categoryName;
    private String creatorName;
}