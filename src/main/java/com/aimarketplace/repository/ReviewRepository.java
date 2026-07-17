package com.aimarketplace.repository;

import com.aimarketplace.entity.AiTool;
import com.aimarketplace.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReviewRepository
        extends JpaRepository<Review, Long> {


    List<Review> findByAiTool(AiTool aiTool);


}