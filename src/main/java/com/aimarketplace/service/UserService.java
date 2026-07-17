package com.aimarketplace.service;

import com.aimarketplace.dto.request.RoleUpdateRequest;
import com.aimarketplace.dto.response.UserResponse;
import com.aimarketplace.entity.User;
import com.aimarketplace.exception.ResourceNotFoundException;
import com.aimarketplace.mapper.UserMapper;
import com.aimarketplace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;

    private final UserMapper userMapper;



    public UserResponse getUser(Long id) {


        User user =
                userRepository.findById(id)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "User not found"
                                )
                        );


        return userMapper.toResponse(user);

    }



    public UserResponse updateRole(
            Long userId,
            RoleUpdateRequest request
    ) {


        User user =
                userRepository.findById(userId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "User not found"
                                )
                        );


        user.setRole(request.getRole());


        userRepository.save(user);


        return userMapper.toResponse(user);

    }

}