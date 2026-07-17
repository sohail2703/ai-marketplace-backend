package com.aimarketplace.controller;


import com.aimarketplace.dto.request.ChatRequest;
import com.aimarketplace.dto.response.ChatResponse;
import com.aimarketplace.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatController extends BaseController {


    private final ChatService chatService;



    @PostMapping
    public ResponseEntity<ChatResponse> chat(
            @RequestBody ChatRequest request
    ){

        return ok(
                chatService.chat(request)
        );

    }

}