package com.aimarketplace.repository;

import com.aimarketplace.entity.Category;
import com.aimarketplace.entity.Tool;
import com.aimarketplace.entity.User;
import com.aimarketplace.enums.ToolStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToolRepository extends JpaRepository<Tool, Long> {

    Page<Tool> findByStatus(ToolStatus status, Pageable pageable);

    Page<Tool> findByCategory(Category category, Pageable pageable);

    Page<Tool> findByCreator(User creator, Pageable pageable);

    Page<Tool> findByToolNameContainingIgnoreCase(String keyword, Pageable pageable);
}