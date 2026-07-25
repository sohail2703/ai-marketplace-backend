package com.aimarketplace.repository;

import com.aimarketplace.entity.Favorite;
import com.aimarketplace.entity.Tool;
import com.aimarketplace.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Page<Favorite> findByUser(User user, Pageable pageable);

    Optional<Favorite> findByUserAndTool(User user, Tool tool);

    boolean existsByUserAndTool(User user, Tool tool);
}