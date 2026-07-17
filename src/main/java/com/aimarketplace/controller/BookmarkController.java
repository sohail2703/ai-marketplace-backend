package com.aimarketplace.controller;


import com.aimarketplace.entity.Bookmark;
import com.aimarketplace.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/bookmarks")
@RequiredArgsConstructor
public class BookmarkController extends BaseController {


    private final BookmarkService bookmarkService;



    @PostMapping("/{toolId}")
    public ResponseEntity<Void> bookmark(
            @PathVariable Long toolId
    ){

        bookmarkService.bookmark(toolId);

        return ResponseEntity.ok().build();

    }



    @GetMapping
    public ResponseEntity<List<Bookmark>> getBookmarks(){

        return ok(
                bookmarkService.getMyBookmarks()
        );

    }



    @DeleteMapping("/{toolId}")
    public ResponseEntity<Void> remove(
            @PathVariable Long toolId
    ){

        bookmarkService.removeBookmark(toolId);

        return ResponseEntity.noContent().build();

    }

}