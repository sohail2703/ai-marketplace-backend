package com.aimarketplace.service;

import com.aimarketplace.dto.request.AuthRequest;
import com.aimarketplace.dto.request.RegisterRequest;
import com.aimarketplace.dto.response.AuthResponse;
import com.aimarketplace.entity.User;
import com.aimarketplace.enums.Role;
import com.aimarketplace.exception.ResourceNotFoundException;
import com.aimarketplace.repository.UserRepository;
import com.aimarketplace.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;


    public AuthResponse register(RegisterRequest request) {


        if(userRepository.existsByEmail(request.getEmail())) {

            throw new RuntimeException("Email already registered");

        }


        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(
                        passwordEncoder.encode(request.getPassword())
                )
                .role(Role.ROLE_USER)
                .enabled(true)
                .build();


        userRepository.save(user);


        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );


        String token =
                jwtTokenProvider.generateToken(authentication);



        return AuthResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .userId(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

    }



    public AuthResponse login(AuthRequest request) {


        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );


        User user =
                userRepository.findByEmail(request.getEmail())
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "User not found"
                                )
                        );


        String token =
                jwtTokenProvider.generateToken(authentication);



        return AuthResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .userId(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

    }


}