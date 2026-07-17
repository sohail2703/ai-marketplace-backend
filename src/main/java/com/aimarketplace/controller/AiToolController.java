package com.aimarketplace.controller;


import com.aimarketplace.dto.request.AiToolRequest;
import com.aimarketplace.dto.response.AiToolResponse;
import com.aimarketplace.service.AiToolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/tools")
@RequiredArgsConstructor
public class AiToolController extends BaseController {


    private final AiToolService aiToolService;



    @PostMapping
    @PreAuthorize("hasAnyRole('PROVIDER','ADMIN')")
    public ResponseEntity<AiToolResponse> createTool(
            @RequestBody AiToolRequest request
    ){

        return ok(
                aiToolService.createTool(request)
        );

    }



    @GetMapping
    public ResponseEntity<List<AiToolResponse>> getAllTools(){

        return ok(
                aiToolService.getAllTools()
        );

    }



    @GetMapping("/{id}")
    public ResponseEntity<AiToolResponse> getTool(
            @PathVariable Long id
    ){

        return ok(
                aiToolService.getTool(id)
        );

    }



    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AiToolResponse> approveTool(
            @PathVariable Long id
    ){

        return ok(
                aiToolService.approveTool(id)
        );

    }

}