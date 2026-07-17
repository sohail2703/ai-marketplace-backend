package com.aimarketplace.repository;

import com.aimarketplace.entity.AiTool;
import com.aimarketplace.entity.Bookmark;
import com.aimarketplace.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    List<Bookmark> findByUser(User user);

    Optional<Bookmark> findByUserAndAiTool(User user, AiTool aiTool);

}