package com.aimarketplace.repository;

import com.aimarketplace.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriteRepository
        extends JpaRepository<Favorite, Long> {

    boolean existsByUserIdAndToolId(
            Long userId,
            Long toolId
    );

    Optional<Favorite> findByUserIdAndToolId(
            Long userId,
            Long toolId
    );
}