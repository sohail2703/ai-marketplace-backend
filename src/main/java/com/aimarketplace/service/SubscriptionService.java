package com.aimarketplace.service;

import com.aimarketplace.dto.request.SubscriptionPlanRequest;
import com.aimarketplace.dto.response.SubscriptionPlanResponse;
import com.aimarketplace.entity.SubscriptionPlan;
import com.aimarketplace.entity.User;
import com.aimarketplace.entity.UserSubscription;
import com.aimarketplace.exception.ResourceNotFoundException;
import com.aimarketplace.mapper.SubscriptionMapper;
import com.aimarketplace.repository.SubscriptionPlanRepository;
import com.aimarketplace.repository.UserRepository;
import com.aimarketplace.repository.UserSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {


    private final SubscriptionPlanRepository subscriptionPlanRepository;

    private final UserSubscriptionRepository userSubscriptionRepository;

    private final UserRepository userRepository;

    private final SubscriptionMapper subscriptionMapper;



    public SubscriptionPlanResponse createPlan(
            SubscriptionPlanRequest request
    ) {

        SubscriptionPlan plan =
                SubscriptionPlan.builder()
                        .name(request.getName())
                        .tier(request.getTier())
                        .description(request.getDescription())
                        .price(request.getPrice())
                        .durationInDays(request.getDurationInDays())
                        .build();


        return subscriptionMapper.toResponse(
                subscriptionPlanRepository.save(plan)
        );

    }



    public List<SubscriptionPlanResponse> getPlans() {


        return subscriptionPlanRepository.findAll()
                .stream()
                .map(subscriptionMapper::toResponse)
                .toList();

    }



    public void subscribe(Long planId) {


        User user = getCurrentUser();


        SubscriptionPlan plan =
                subscriptionPlanRepository.findById(planId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Subscription plan not found"
                                )
                        );


        UserSubscription subscription =
                UserSubscription.builder()
                        .user(user)
                        .subscriptionPlan(plan)
                        .startDate(LocalDate.now())
                        .endDate(
                                LocalDate.now()
                                        .plusDays(plan.getDurationInDays())
                        )
                        .active(true)
                        .build();


        userSubscriptionRepository.save(subscription);

    }



    private User getCurrentUser(){

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();


        return userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "User not found"
                        )
                );

    }

}