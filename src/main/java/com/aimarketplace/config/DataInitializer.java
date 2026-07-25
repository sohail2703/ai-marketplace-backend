package com.aimarketplace.config;

import com.aimarketplace.entity.Role;
import com.aimarketplace.enums.RoleType;
import com.aimarketplace.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {

        createRoleIfNotExists(RoleType.ROLE_USER);
        createRoleIfNotExists(RoleType.ROLE_CREATOR);
        createRoleIfNotExists(RoleType.ROLE_ADMIN);
    }

    private void createRoleIfNotExists(RoleType roleType) {

        if (!roleRepository.existsByName(roleType)) {

            Role role = Role.builder()
                    .name(roleType)
                    .build();

            roleRepository.save(role);
        }
    }
}