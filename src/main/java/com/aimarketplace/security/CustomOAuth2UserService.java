package com.aimarketplace.security;

import com.aimarketplace.entity.Role;
import com.aimarketplace.entity.User;
import com.aimarketplace.enums.ProviderType;
import com.aimarketplace.enums.RoleType;
import com.aimarketplace.repository.RoleRepository;
import com.aimarketplace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService
        extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public OAuth2User loadUser(
            OAuth2UserRequest userRequest) {

        OAuth2User oauth2User =
                super.loadUser(userRequest);

        String email =
                oauth2User.getAttribute("email");

        String name =
                oauth2User.getAttribute("name");

        User user =
                userRepository.findByEmail(email)
                        .orElseGet(() ->
                                createGoogleUser(
                                        email,
                                        name
                                ));

        return new CustomOAuth2User(
                oauth2User,
                user
        );
    }

    private User createGoogleUser(
            String email,
            String name) {

        Role userRole =
                roleRepository.findByName(
                        RoleType.ROLE_USER
                ).orElseThrow();

        User user = User.builder()
                .email(email)
                .fullName(name)
                .provider(ProviderType.GOOGLE)
                .enabled(true)
                .roles(Set.of(userRole))
                .build();

        return userRepository.save(user);
    }
}