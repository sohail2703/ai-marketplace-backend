package com.aimarketplace.mapper;

import com.aimarketplace.dto.response.SubscriptionPlanResponse;
import com.aimarketplace.entity.SubscriptionPlan;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    SubscriptionPlanResponse toResponse(
            SubscriptionPlan subscriptionPlan
    );

}