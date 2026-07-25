package com.aimarketplace.security;

import com.aimarketplace.entity.User;
import com.aimarketplace.enums.ProviderType;
import com.aimarketplace.repository.RoleRepository;
import com.aimarketplace.repository.UserRepository;
import com.aimarketplace.enums.RoleType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        org.springframework.security.core.Authentication authentication)
            throws IOException, ServletException {

        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {

                    User newUser = User.builder()
                            .fullName(name)
                            .email(email)
                            .provider(ProviderType.GOOGLE)
                            .enabled(true)
                            .roles(Set.of(
                                    roleRepository.findByName(RoleType.ROLE_USER)
                                            .orElseThrow()))
                            .build();

                    return userRepository.save(newUser);

                });

        UserDetails userDetails = new CustomUserDetails(user);

        String token = jwtService.generateToken(userDetails);

        response.sendRedirect(
                "http://localhost:3000/oauth2/success?token=" + token
        );
    }
}