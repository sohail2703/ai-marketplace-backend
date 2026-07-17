package com.aimarketplace.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransactionRequest {

    @NotNull
    private Long subscriptionPlanId;

    @NotBlank
    private String paymentMethod;

}