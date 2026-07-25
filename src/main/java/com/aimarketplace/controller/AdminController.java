package com.aimarketplace.controller;

import com.aimarketplace.dto.response.ApiResponse;
import com.aimarketplace.dto.response.PageResponse;
import com.aimarketplace.dto.response.UserResponse;
import com.aimarketplace.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    public ApiResponse<PageResponse<UserResponse>>
    getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (page < 0 || size < 1 || size > 100) {
            throw new IllegalArgumentException(
                    "Invalid pagination parameters"
            );
        }

        return ApiResponse
                .<PageResponse<UserResponse>>builder()
                .success(true)
                .message("Users fetched successfully")
                .data(
                        adminService.getAllUsers(
                                page,
                                size
                        )
                )
                .build();
    }

    @PatchMapping("/users/{userId}/deactivate")
    public ApiResponse<Void> deactivateUser(
            @PathVariable Long userId) {

        adminService.deactivateUser(userId);

        return ApiResponse.<Void>builder()
                .success(true)
                .message("User deactivated successfully")
                .data(null)
                .build();
    }

    @PatchMapping("/users/{userId}/activate")
    public ApiResponse<Void> activateUser(
            @PathVariable Long userId) {

        adminService.activateUser(userId);

        return ApiResponse.<Void>builder()
                .success(true)
                .message("User activated successfully")
                .data(null)
                .build();
    }
}