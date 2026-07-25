package com.aimarketplace.repository;

import com.aimarketplace.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository
        extends JpaRepository<Review, Long> {

    boolean existsByUserIdAndToolId(
            Long userId,
            Long toolId
    );

    Page<Review> findByToolId(
            Long toolId,
            Pageable pageable
    );
}