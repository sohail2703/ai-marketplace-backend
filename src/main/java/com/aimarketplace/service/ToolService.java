package com.aimarketplace.service;

import com.aimarketplace.dto.request.ToolRequest;
import com.aimarketplace.dto.response.PageResponse;
import com.aimarketplace.dto.response.ToolResponse;
import com.aimarketplace.entity.Category;
import com.aimarketplace.entity.Tool;
import com.aimarketplace.entity.User;
import com.aimarketplace.enums.RoleType;
import com.aimarketplace.enums.ToolStatus;
import com.aimarketplace.exception.BadRequestException;
import com.aimarketplace.exception.ResourceNotFoundException;
import com.aimarketplace.mapper.ToolMapper;
import com.aimarketplace.repository.CategoryRepository;
import com.aimarketplace.repository.ToolRepository;
import com.aimarketplace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ToolService {

    private final ToolRepository toolRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ToolMapper toolMapper;

    @Transactional
    @CacheEvict(value = "tools", allEntries = true)
    public ToolResponse createTool(
            ToolRequest request,
            Authentication authentication) {

        User creator = getAuthenticatedUser(authentication);

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id: "
                                        + request.getCategoryId()
                        ));

        Tool tool = Tool.builder()
                .toolName(request.getToolName())
                .description(request.getDescription())
                .websiteUrl(request.getWebsiteUrl())
                .pricing(request.getPricing())
                .category(category)
                .creator(creator)
                .status(ToolStatus.APPROVED)
                .build();

        return toolMapper.toResponse(
                toolRepository.save(tool)
        );
    }

    @Transactional(readOnly = true)
    @Cacheable(
            value = "tools",
            key = "#page + '-' + #size + '-' + #keyword + '-' + #categoryId + '-' + #status"
    )
    public PageResponse<ToolResponse> getTools(
            int page,
            int size,
            String keyword,
            Long categoryId,
            ToolStatus status) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Page<Tool> toolPage = toolRepository.searchTools(
                keyword,
                categoryId,
                status,
                pageable
        );

        return PageResponse.<ToolResponse>builder()
                .content(
                        toolPage.getContent()
                                .stream()
                                .map(toolMapper::toResponse)
                                .toList()
                )
                .page(toolPage.getNumber())
                .size(toolPage.getSize())
                .totalElements(toolPage.getTotalElements())
                .totalPages(toolPage.getTotalPages())
                .last(toolPage.isLast())
                .build();
    }

    @Transactional(readOnly = true)
    public ToolResponse getTool(Long id) {

        Tool tool = toolRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tool not found with id: " + id
                        ));

        return toolMapper.toResponse(tool);
    }

    @Transactional
    @CacheEvict(value = "tools", allEntries = true)
    public ToolResponse updateTool(
            Long id,
            ToolRequest request,
            Authentication authentication) {

        User currentUser = getAuthenticatedUser(authentication);

        Tool tool = toolRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tool not found with id: " + id
                        ));

        validateOwnership(tool, currentUser);

        Category category = categoryRepository.findById(
                        request.getCategoryId()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id: "
                                        + request.getCategoryId()
                        ));

        tool.setToolName(request.getToolName());
        tool.setDescription(request.getDescription());
        tool.setWebsiteUrl(request.getWebsiteUrl());
        tool.setCategory(category);

        return toolMapper.toResponse(
                toolRepository.save(tool)
        );
    }

    @Transactional
    @CacheEvict(value = "tools", allEntries = true)
    public void deleteTool(
            Long id,
            Authentication authentication) {

        User currentUser = getAuthenticatedUser(authentication);

        Tool tool = toolRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tool not found with id: " + id
                        ));

        validateOwnership(tool, currentUser);

        toolRepository.delete(tool);
    }

    private User getAuthenticatedUser(
            Authentication authentication) {

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Authenticated user not found"
                        ));
    }

    private void validateOwnership(
            Tool tool,
            User currentUser) {

        boolean isAdmin = currentUser.getRoles()
                .stream()
                .anyMatch(role ->
                        role.getName() == RoleType.ROLE_ADMIN
                );

        boolean isOwner =
                tool.getCreator().getId()
                        .equals(currentUser.getId());

        if (!isAdmin && !isOwner) {
            throw new BadRequestException(
                    "You are not authorized to modify this tool"
            );
        }
    }
}