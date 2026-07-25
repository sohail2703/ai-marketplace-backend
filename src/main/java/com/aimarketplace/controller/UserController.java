package com.aimarketplace.controller;

import com.aimarketplace.dto.response.ApiResponse;
import com.aimarketplace.dto.response.UserResponse;
import com.aimarketplace.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ApiResponse<UserResponse> getCurrentUser(
            Authentication authentication) {

        return ApiResponse.<UserResponse>builder()
                .success(true)
                .message("User profile fetched successfully")
                .data(
                        userService.getCurrentUser(
                                authentication.getName()
                        )
                )
                .build();
    }
}