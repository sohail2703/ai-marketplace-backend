package com.aimarketplace.dto.request;

import com.aimarketplace.enums.PlanTier;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SubscriptionPlanRequest {

    @NotBlank
    private String name;

    @NotNull
    private PlanTier tier;

    private String description;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Integer durationInDays;

}