package com.aimarketplace.dto.response;

import com.aimarketplace.enums.PricingType;
import com.aimarketplace.enums.ToolStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AiToolResponse {

    private Long id;

    private String name;

    private String description;

    private String websiteUrl;

    private String logoUrl;

    private PricingType pricingType;

    private BigDecimal price;

    private ToolStatus status;

    private Double averageRating;

    private Integer totalReviews;

    private String providerName;

    private String categoryName;

}