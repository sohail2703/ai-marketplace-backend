package com.aimarketplace.controller;

import com.aimarketplace.dto.request.ToolRequest;
import com.aimarketplace.dto.response.ApiResponse;
import com.aimarketplace.dto.response.PageResponse;
import com.aimarketplace.dto.response.ToolResponse;
import com.aimarketplace.enums.ToolStatus;
import com.aimarketplace.service.ToolService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tools")
@RequiredArgsConstructor
public class ToolController {

    private final ToolService toolService;

    @PostMapping
    @PreAuthorize("hasAnyRole('CREATOR', 'ADMIN')")
    public ApiResponse<ToolResponse> createTool(
            @Valid @RequestBody ToolRequest request,
            Authentication authentication) {

        return ApiResponse.<ToolResponse>builder()
                .success(true)
                .message("Tool created successfully")
                .data(
                        toolService.createTool(
                                request,
                                authentication
                        )
                )
                .build();
    }

    @GetMapping
    public ApiResponse<PageResponse<ToolResponse>> getTools(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) ToolStatus status) {

        if (page < 0 || size < 1 || size > 100) {
            throw new IllegalArgumentException(
                    "Invalid pagination parameters"
            );
        }

        return ApiResponse.<PageResponse<ToolResponse>>builder()
                .success(true)
                .message("Tools fetched successfully")
                .data(
                        toolService.getTools(
                                page,
                                size,
                                keyword,
                                categoryId,
                                status
                        )
                )
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ToolResponse> getTool(
            @PathVariable Long id) {

        return ApiResponse.<ToolResponse>builder()
                .success(true)
                .message("Tool fetched successfully")
                .data(toolService.getTool(id))
                .build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CREATOR', 'ADMIN')")
    public ApiResponse<ToolResponse> updateTool(
            @PathVariable Long id,
            @Valid @RequestBody ToolRequest request,
            Authentication authentication) {

        return ApiResponse.<ToolResponse>builder()
                .success(true)
                .message("Tool updated successfully")
                .data(
                        toolService.updateTool(
                                id,
                                request,
                                authentication
                        )
                )
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CREATOR', 'ADMIN')")
    public ApiResponse<Void> deleteTool(
            @PathVariable Long id,
            Authentication authentication) {

        toolService.deleteTool(
                id,
                authentication
        );

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Tool deleted successfully")
                .data(null)
                .build();
    }
}