package com.aimarketplace.service;

import com.aimarketplace.dto.response.AiToolResponse;
import com.aimarketplace.mapper.AiToolMapper;
import com.aimarketplace.repository.AiToolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {


    private final AiToolRepository aiToolRepository;

    private final AiToolMapper aiToolMapper;



    public List<AiToolResponse> search(String keyword){


        return aiToolRepository
                .findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(aiToolMapper::toResponse)
                .toList();

    }

}