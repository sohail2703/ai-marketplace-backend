package com.aimarketplace.service;

import com.aimarketplace.dto.response.AiToolResponse;
import com.aimarketplace.mapper.AiToolMapper;
import com.aimarketplace.repository.AiToolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {


    private final AiToolRepository aiToolRepository;

    private final AiToolMapper aiToolMapper;



    public List<AiToolResponse> recommendTools(){


        return aiToolRepository.findAll()
                .stream()
                .limit(10)
                .map(aiToolMapper::toResponse)
                .toList();

    }

}