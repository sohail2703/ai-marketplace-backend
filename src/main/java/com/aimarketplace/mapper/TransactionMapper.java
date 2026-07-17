package com.aimarketplace.mapper;

import com.aimarketplace.dto.response.TransactionResponse;
import com.aimarketplace.entity.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionResponse toResponse(
            Transaction transaction
    );

}