package com.aimarketplace.service;

import com.aimarketplace.dto.request.ReviewRequest;
import com.aimarketplace.dto.response.ReviewResponse;
import com.aimarketplace.entity.AiTool;
import com.aimarketplace.entity.Review;
import com.aimarketplace.entity.User;
import com.aimarketplace.exception.ResourceNotFoundException;
import com.aimarketplace.mapper.ReviewMapper;
import com.aimarketplace.repository.AiToolRepository;
import com.aimarketplace.repository.ReviewRepository;
import com.aimarketplace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {


    private final ReviewRepository reviewRepository;

    private final AiToolRepository aiToolRepository;

    private final UserRepository userRepository;

    private final ReviewMapper reviewMapper;


    public ReviewResponse addReview(Long toolId, ReviewRequest request) {


        User user = getCurrentUser();


        AiTool aiTool =
                aiToolRepository.findById(toolId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "AI Tool not found"
                                )
                        );


        Review review = Review.builder()
                .rating(request.getRating())
                .comment(request.getComment())
                .user(user)
                .aiTool(aiTool)
                .build();


        return reviewMapper.toResponse(
                reviewRepository.save(review)
        );

    }



    public List<ReviewResponse> getReviews(Long toolId) {


        AiTool aiTool =
                aiToolRepository.findById(toolId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "AI Tool not found"
                                )
                        );


        return reviewRepository.findByAiTool(aiTool)
                .stream()
                .map(reviewMapper::toResponse)
                .toList();

    }



    public void deleteReview(Long id) {


        Review review =
                reviewRepository.findById(id)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Review not found"
                                )
                        );


        reviewRepository.delete(review);

    }



    private User getCurrentUser() {


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