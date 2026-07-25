package com.aimarketplace.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ToolRequest {

    @NotBlank
    private String toolName;

    @NotBlank
    private String description;

    @NotBlank
    private String websiteUrl;

    private String imageUrl;

    @NotBlank
    private String pricing;

    @NotNull
    private Long categoryId;
}