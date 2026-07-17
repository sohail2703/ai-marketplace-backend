package com.aimarketplace.dto.request;

import com.aimarketplace.enums.PricingType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AiToolRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String websiteUrl;

    private String logoUrl;

    @NotNull
    private PricingType pricingType;

    private BigDecimal price;

    @NotNull
    private Long categoryId;

}