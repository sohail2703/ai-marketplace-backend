package com.aimarketplace.dto.response;

import com.aimarketplace.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

    private Long id;

    private String fullName;

    private String email;

    private String profileImageUrl;

    private Role role;

    private Boolean enabled;

}