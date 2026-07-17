package com.aimarketplace.controller;


import com.aimarketplace.dto.request.ReviewRequest;
import com.aimarketplace.dto.response.ReviewResponse;
import com.aimarketplace.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController extends BaseController {


    private final ReviewService reviewService;



    @PostMapping("/{toolId}")
    public ResponseEntity<ReviewResponse> addReview(
            @PathVariable Long toolId,
            @RequestBody ReviewRequest request
    ){

        return ok(
                reviewService.addReview(toolId, request)
        );

    }



    @GetMapping("/{toolId}")
    public ResponseEntity<List<ReviewResponse>> getReviews(
            @PathVariable Long toolId
    ){

        return ok(
                reviewService.getReviews(toolId)
        );

    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ){

        reviewService.deleteReview(id);

        return ResponseEntity.noContent().build();

    }

}