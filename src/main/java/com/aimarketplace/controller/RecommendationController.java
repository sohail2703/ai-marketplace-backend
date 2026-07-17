package com.aimarketplace.controller;


import com.aimarketplace.dto.response.AiToolResponse;
import com.aimarketplace.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/recommendations")
@RequiredArgsConstructor
public class RecommendationController extends BaseController {


    private final RecommendationService recommendationService;



    @GetMapping
    public ResponseEntity<List<AiToolResponse>> recommend(){

        return ok(
                recommendationService.recommendTools()
        );

    }

}