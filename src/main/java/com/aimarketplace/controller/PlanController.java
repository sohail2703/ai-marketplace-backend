package com.aimarketplace.controller;


import com.aimarketplace.dto.request.SubscriptionPlanRequest;
import com.aimarketplace.dto.response.SubscriptionPlanResponse;
import com.aimarketplace.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/plans")
@RequiredArgsConstructor
public class PlanController extends BaseController {


    private final SubscriptionService subscriptionService;



    @PostMapping
    public ResponseEntity<SubscriptionPlanResponse> createPlan(
            @RequestBody SubscriptionPlanRequest request
    ){

        return ok(
                subscriptionService.createPlan(request)
        );

    }



    @GetMapping
    public ResponseEntity<List<SubscriptionPlanResponse>> getPlans(){

        return ok(
                subscriptionService.getPlans()
        );

    }

}