package com.aimarketplace.controller;

import com.aimarketplace.dto.request.ReviewRequest;
import com.aimarketplace.dto.response.ApiResponse;
import com.aimarketplace.dto.response.PageResponse;
import com.aimarketplace.dto.response.ReviewResponse;
import com.aimarketplace.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tools/{toolId}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ApiResponse<ReviewResponse> createReview(
            @PathVariable Long toolId,
            @Valid @RequestBody ReviewRequest request,
            Authentication authentication) {

        return ApiResponse.<ReviewResponse>builder()
                .success(true)
                .message("Review created successfully")
                .data(
                        reviewService.createReview(
                                toolId,
                                request,
                                authentication.getName()
                        )
                )
                .build();
    }

    @GetMapping
    public ApiResponse<PageResponse<ReviewResponse>>
    getToolReviews(
            @PathVariable Long toolId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ApiResponse.<PageResponse<ReviewResponse>>builder()
                .success(true)
                .message("Reviews fetched successfully")
                .data(
                        reviewService.getToolReviews(
                                toolId,
                                page,
                                size
                        )
                )
                .build();
    }

    @DeleteMapping("/{reviewId}")
    public ApiResponse<Void> deleteReview(
            @PathVariable Long reviewId,
            Authentication authentication) {

        reviewService.deleteReview(
                reviewId,
                authentication.getName()
        );

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Review deleted successfully")
                .data(null)
                .build();
    }
}