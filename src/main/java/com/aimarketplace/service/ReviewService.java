package com.aimarketplace.service;

import com.aimarketplace.dto.request.ReviewRequest;
import com.aimarketplace.dto.response.PageResponse;
import com.aimarketplace.dto.response.ReviewResponse;
import com.aimarketplace.entity.Review;
import com.aimarketplace.entity.Tool;
import com.aimarketplace.entity.User;
import com.aimarketplace.exception.BadRequestException;
import com.aimarketplace.exception.ResourceNotFoundException;
import com.aimarketplace.mapper.ReviewMapper;
import com.aimarketplace.repository.ReviewRepository;
import com.aimarketplace.repository.ToolRepository;
import com.aimarketplace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ToolRepository toolRepository;
    private final UserRepository userRepository;
    private final ReviewMapper reviewMapper;

    @Transactional
    public ReviewResponse createReview(
            Long toolId,
            ReviewRequest request,
            String email) {

        User user = getUser(email);

        Tool tool = toolRepository.findById(toolId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tool not found"
                        ));

        if (reviewRepository.existsByUserIdAndToolId(
                user.getId(),
                toolId)) {

            throw new BadRequestException(
                    "You have already reviewed this tool"
            );
        }

        Review review = Review.builder()
                .user(user)
                .tool(tool)
                .rating(request.getRating())
                .comment(request.getComment())
                .build();

        return reviewMapper.toResponse(
                reviewRepository.save(review)
        );
    }

    @Transactional(readOnly = true)
    public PageResponse<ReviewResponse> getToolReviews(
            Long toolId,
            int page,
            int size) {

        Pageable pageable =
                PageRequest.of(page, size);

        Page<Review> reviewPage =
                reviewRepository
                        .findByToolId(
                                toolId,
                                pageable
                        );

        return PageResponse.<ReviewResponse>builder()
                .content(
                        reviewPage.getContent()
                                .stream()
                                .map(reviewMapper::toResponse)
                                .toList()
                )
                .page(reviewPage.getNumber())
                .size(reviewPage.getSize())
                .totalElements(
                        reviewPage.getTotalElements()
                )
                .totalPages(
                        reviewPage.getTotalPages()
                )
                .last(reviewPage.isLast())
                .build();
    }

    @Transactional
    public void deleteReview(
            Long reviewId,
            String email) {

        User user = getUser(email);

        Review review =
                reviewRepository.findById(reviewId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Review not found"
                                ));

        if (!review.getUser()
                .getId()
                .equals(user.getId())) {

            throw new BadRequestException(
                    "You can delete only your own review"
            );
        }

        reviewRepository.delete(review);
    }

    private User getUser(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));
    }
}