package com.aimarketplace.dto.request;

import com.aimarketplace.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoleUpdateRequest {

    @NotNull(message = "Role is required")
    private Role role;

}