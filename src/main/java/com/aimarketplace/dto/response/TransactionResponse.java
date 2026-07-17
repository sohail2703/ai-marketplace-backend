package com.aimarketplace.dto.response;

import com.aimarketplace.enums.TransactionStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransactionResponse {

    private Long id;

    private String transactionId;

    private BigDecimal amount;

    private TransactionStatus status;

    private String paymentMethod;

}