package com.aimarketplace.service;

import com.aimarketplace.entity.Favorite;
import com.aimarketplace.entity.Tool;
import com.aimarketplace.entity.User;
import com.aimarketplace.exception.BadRequestException;
import com.aimarketplace.exception.ResourceNotFoundException;
import com.aimarketplace.repository.FavoriteRepository;
import com.aimarketplace.repository.ToolRepository;
import com.aimarketplace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final ToolRepository toolRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addFavorite(Long toolId, String email) {

        User user = getUser(email);

        Tool tool = toolRepository.findById(toolId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tool not found with id: " + toolId
                        ));

        if (favoriteRepository.existsByUserIdAndToolId(
                user.getId(),
                toolId)) {

            throw new BadRequestException(
                    "Tool is already in favorites"
            );
        }

        Favorite favorite = Favorite.builder()
                .user(user)
                .tool(tool)
                .build();

        favoriteRepository.save(favorite);
    }

    @Transactional
    public void removeFavorite(
            Long toolId,
            String email) {

        User user = getUser(email);

        Favorite favorite =
                favoriteRepository
                        .findByUserIdAndToolId(
                                user.getId(),
                                toolId
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Favorite not found"
                                ));

        favoriteRepository.delete(favorite);
    }

    private User getUser(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));
    }
}