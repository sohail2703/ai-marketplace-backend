package com.aimarketplace.controller;


import com.aimarketplace.dto.request.RoleUpdateRequest;
import com.aimarketplace.dto.response.UserResponse;
import com.aimarketplace.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController extends BaseController {


    private final UserService userService;



    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(
            @PathVariable Long id
    ){

        return ok(
                userService.getUser(id)
        );

    }



    @PutMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> updateRole(
            @PathVariable Long id,
            @RequestBody RoleUpdateRequest request
    ){

        return ok(
                userService.updateRole(id, request)
        );

    }

}