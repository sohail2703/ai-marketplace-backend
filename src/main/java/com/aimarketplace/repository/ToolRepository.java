package com.aimarketplace.repository;

import com.aimarketplace.entity.Tool;
import com.aimarketplace.enums.ToolStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface ToolRepository
        extends JpaRepository<Tool, Long> {

    @Query("""
            SELECT t
            FROM Tool t
            WHERE
                (:keyword IS NULL
                 OR LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
                 OR LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%')))
            AND (:categoryId IS NULL
                 OR t.category.id = :categoryId)
            AND (:status IS NULL
                 OR t.status = :status)
            """)
    Page<Tool> searchTools(
            @Param("keyword") String keyword,
            @Param("categoryId") Long categoryId,
            @Param("status") ToolStatus status,
            Pageable pageable
    );
}