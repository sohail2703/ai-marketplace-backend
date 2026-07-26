package com.aimarketplace.controller;

import com.aimarketplace.dto.request.LoginRequest;
import com.aimarketplace.dto.request.RegisterRequest;
import com.aimarketplace.dto.response.ApiResponse;
import com.aimarketplace.dto.response.JwtResponse;
import com.aimarketplace.dto.response.UserResponse;
import com.aimarketplace.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<UserResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        return ApiResponse.<UserResponse>builder()
                .success(true)
                .message("User registered successfully")
                .data(authService.register(request))
                .build();
    }

    @PostMapping("/login")
    public ApiResponse<JwtResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return ApiResponse.<JwtResponse>builder()
                .success(true)
                .message("Login successful")
                .data(authService.login(request))
                .build();
    }
}