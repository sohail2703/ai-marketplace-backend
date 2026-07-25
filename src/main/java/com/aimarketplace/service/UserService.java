package com.aimarketplace.service;

import com.aimarketplace.dto.response.PageResponse;
import com.aimarketplace.dto.response.UserResponse;
import com.aimarketplace.entity.User;
import com.aimarketplace.exception.ResourceNotFoundException;
import com.aimarketplace.mapper.UserMapper;
import com.aimarketplace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public UserResponse getCurrentUser(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));

        return userMapper.toResponse(user);
    }

    @Transactional(readOnly = true)
    public PageResponse<UserResponse> getAllUsers(
            int page,
            int size) {

        Pageable pageable =
                PageRequest.of(page, size);

        Page<User> userPage =
                userRepository.findAll(pageable);

        return PageResponse.<UserResponse>builder()
                .content(
                        userPage.getContent()
                                .stream()
                                .map(userMapper::toResponse)
                                .toList()
                )
                .page(userPage.getNumber())
                .size(userPage.getSize())
                .totalElements(
                        userPage.getTotalElements()
                )
                .totalPages(
                        userPage.getTotalPages()
                )
                .last(userPage.isLast())
                .build();
    }

    @Transactional
    public void deactivateUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + userId
                        ));

        user.setEnabled(false);

        userRepository.save(user);
    }

    @Transactional
    public void activateUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + userId
                        ));

        user.setEnabled(true);

        userRepository.save(user);
    }
}