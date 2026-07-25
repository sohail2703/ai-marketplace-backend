package com.aimarketplace.controller;

import com.aimarketplace.dto.response.ApiResponse;
import com.aimarketplace.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/{toolId}")
    public ApiResponse<Void> addFavorite(
            @PathVariable Long toolId,
            Authentication authentication) {

        favoriteService.addFavorite(
                toolId,
                authentication.getName()
        );

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Tool added to favorites")
                .data(null)
                .build();
    }

    @DeleteMapping("/{toolId}")
    public ApiResponse<Void> removeFavorite(
            @PathVariable Long toolId,
            Authentication authentication) {

        favoriteService.removeFavorite(
                toolId,
                authentication.getName()
        );

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Tool removed from favorites")
                .data(null)
                .build();
    }
}