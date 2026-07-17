package com.aimarketplace.controller;


import com.aimarketplace.dto.request.CategoryRequest;
import com.aimarketplace.dto.response.CategoryResponse;
import com.aimarketplace.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController extends BaseController {


    private final CategoryService categoryService;



    @PostMapping
    public ResponseEntity<CategoryResponse> create(
            @RequestBody CategoryRequest request
    ){

        return ok(
                categoryService.create(request)
        );

    }



    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAll(){

        return ok(
                categoryService.getAll()
        );

    }



    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getById(
            @PathVariable Long id
    ){

        return ok(
                categoryService.getById(id)
        );

    }

}