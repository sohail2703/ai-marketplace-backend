package com.aimarketplace.controller;


import com.aimarketplace.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController extends BaseController {


    private final SubscriptionService subscriptionService;



    @PostMapping("/{planId}")
    public ResponseEntity<Void> subscribe(
            @PathVariable Long planId
    ){

        subscriptionService.subscribe(planId);

        return ResponseEntity.ok().build();

    }

}