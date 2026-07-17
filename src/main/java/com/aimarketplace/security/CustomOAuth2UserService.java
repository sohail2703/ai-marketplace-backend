package com.aimarketplace.security;

import com.aimarketplace.entity.User;
import com.aimarketplace.enums.Role;
import com.aimarketplace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {


    private final UserRepository userRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {


        OAuth2User oauth2User = super.loadUser(userRequest);


        String email = oauth2User.getAttribute("email");

        String name = oauth2User.getAttribute("name");


        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {

                    User newUser = User.builder()
                            .fullName(name)
                            .email(email)
                            .password("")
                            .role(Role.ROLE_USER)
                            .provider("GOOGLE")
                            .providerId(
                                    oauth2User.getName()
                            )
                            .build();

                    return userRepository.save(newUser);

                });


        return oauth2User;
    }
}