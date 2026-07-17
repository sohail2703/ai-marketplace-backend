package com.aimarketplace.controller;


import com.aimarketplace.dto.request.AuthRequest;
import com.aimarketplace.dto.request.RegisterRequest;
import com.aimarketplace.dto.response.AuthResponse;
import com.aimarketplace.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController extends BaseController {


    private final AuthService authService;



    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request
    ){

        return ok(
                authService.register(request)
        );

    }



    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody AuthRequest request
    ){

        return ok(
                authService.login(request)
        );

    }


}