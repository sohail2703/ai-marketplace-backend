package com.aimarketplace.service;

import com.aimarketplace.entity.AiTool;
import com.aimarketplace.entity.Bookmark;
import com.aimarketplace.entity.User;
import com.aimarketplace.exception.ResourceNotFoundException;
import com.aimarketplace.repository.AiToolRepository;
import com.aimarketplace.repository.BookmarkRepository;
import com.aimarketplace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {


    private final BookmarkRepository bookmarkRepository;

    private final AiToolRepository aiToolRepository;

    private final UserRepository userRepository;



    public void bookmark(Long toolId) {


        User user = getCurrentUser();


        AiTool aiTool =
                aiToolRepository.findById(toolId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "AI Tool not found"
                                )
                        );


        Bookmark bookmark = Bookmark.builder()
                .user(user)
                .aiTool(aiTool)
                .build();


        bookmarkRepository.save(bookmark);

    }



    public List<Bookmark> getMyBookmarks() {


        return bookmarkRepository.findByUser(
                getCurrentUser()
        );

    }



    public void removeBookmark(Long toolId) {


        User user = getCurrentUser();


        AiTool aiTool =
                aiToolRepository.findById(toolId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "AI Tool not found"
                                )
                        );


        Bookmark bookmark =
                bookmarkRepository.findByUserAndAiTool(
                                user,
                                aiTool
                        )
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Bookmark not found"
                                )
                        );


        bookmarkRepository.delete(bookmark);

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