package com.aimarketplace.repository;

import com.aimarketplace.entity.Review;
import com.aimarketplace.entity.Tool;
import com.aimarketplace.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByTool(Tool tool);

    Optional<Review> findByUserAndTool(User user, Tool tool);

    boolean existsByUserAndTool(User user, Tool tool);
}