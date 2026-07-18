package com.aimarketplace.service;

import com.aimarketplace.dto.request.AiToolRequest;
import com.aimarketplace.dto.response.AiToolResponse;
import com.aimarketplace.entity.AiTool;
import com.aimarketplace.entity.Category;
import com.aimarketplace.entity.User;
import com.aimarketplace.enums.Role;
import com.aimarketplace.enums.ToolStatus;
import com.aimarketplace.exception.ResourceNotFoundException;
import com.aimarketplace.mapper.AiToolMapper;
import com.aimarketplace.repository.AiToolRepository;
import com.aimarketplace.repository.CategoryRepository;
import com.aimarketplace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AiToolService {


    private final AiToolRepository aiToolRepository;

    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    private final AiToolMapper aiToolMapper;



    public AiToolResponse createTool(AiToolRequest request) {


        User provider = getCurrentUser();


        if(provider.getRole() != Role.ROLE_PROVIDER
                && provider.getRole() != Role.ROLE_ADMIN) {

            throw new RuntimeException(
                    "Only providers can create AI tools"
            );
        }


        Category category =
                categoryRepository.findById(request.getCategoryId())
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Category not found"
                                )
                        );


        AiTool aiTool = AiTool.builder()
                .name(request.getName())
                .description(request.getDescription())
                .websiteUrl(request.getWebsiteUrl())
                .logoUrl(request.getLogoUrl())
                .pricingType(request.getPricingType())
                .price(request.getPrice())
                .category(category)
                .provider(provider)
                .status(ToolStatus.PENDING)
                .build();


        return aiToolMapper.toResponse(
                aiToolRepository.save(aiTool)
        );

    }



    @Cacheable(value = "aitool", key = "#id")
    public List<AiToolResponse> getAllTools() {


        return aiToolRepository.findAll()
                .stream()
                .map(aiToolMapper::toResponse)
                .toList();

    }



    public AiToolResponse getTool(Long id) {


        AiTool aiTool =
                aiToolRepository.findById(id)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "AI Tool not found"
                                )
                        );


        return aiToolMapper.toResponse(aiTool);

    }



    public AiToolResponse approveTool(Long id) {


        AiTool aiTool =
                aiToolRepository.findById(id)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "AI Tool not found"
                                )
                        );


        aiTool.setStatus(ToolStatus.APPROVED);


        return aiToolMapper.toResponse(
                aiToolRepository.save(aiTool)
        );

    }



    private User getCurrentUser() {


        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();


        return userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "User not found"
                        )
                );

    }

}