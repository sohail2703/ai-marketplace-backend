package com.aimarketplace.dto.response;

import com.aimarketplace.enums.PlanTier;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class SubscriptionPlanResponse {

    private Long id;

    private String name;

    private PlanTier tier;

    private String description;

    private BigDecimal price;

    private Integer durationInDays;

}