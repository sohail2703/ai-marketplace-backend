package com.aimarketplace.controller;


import com.aimarketplace.dto.response.AiToolResponse;
import com.aimarketplace.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
public class SearchController extends BaseController {


    private final SearchService searchService;



    @GetMapping
    public ResponseEntity<List<AiToolResponse>> search(
            @RequestParam String keyword
    ){

        return ok(
                searchService.search(keyword)
        );

    }

}