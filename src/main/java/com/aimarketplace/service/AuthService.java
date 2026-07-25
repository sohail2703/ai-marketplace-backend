package com.aimarketplace.service;

import com.aimarketplace.dto.request.LoginRequest;
import com.aimarketplace.dto.request.RegisterRequest;
import com.aimarketplace.dto.response.JwtResponse;
import com.aimarketplace.entity.Role;
import com.aimarketplace.entity.User;
import com.aimarketplace.enums.ProviderType;
import com.aimarketplace.enums.RoleType;
import com.aimarketplace.exception.BadRequestException;
import com.aimarketplace.repository.RoleRepository;
import com.aimarketplace.repository.UserRepository;
import com.aimarketplace.security.CustomUserDetails;
import com.aimarketplace.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public JwtResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        Role userRole = roleRepository.findByName(RoleType.ROLE_USER)
                .orElseThrow(() -> new BadRequestException("Default role not found"));

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .provider(ProviderType.LOCAL)
                .enabled(true)
                .roles(Set.of(userRole))
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(new CustomUserDetails(user));

        return JwtResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .build();
    }

    public JwtResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("User not found"));

        String token = jwtService.generateToken(new CustomUserDetails(user));

        return JwtResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .build();
    }

}