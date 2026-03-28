package com.tramplin.controller;

import com.tramplin.dto.admin.response.UserResponse;
import com.tramplin.entity.User;
import com.tramplin.service.UserControlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class UserControlController {

    private final UserControlService userControlService;

    @GetMapping
    @Operation(summary = "Get users list with optional email filter (SUPER ADMIN only)")
    public List<UserResponse> getUsers(
            @RequestParam(required = false) String email,
            @RequestHeader("X-Admin-Email") String adminEmail
    ) {
        return userControlService.getUsers(email, adminEmail);
    }

    @PutMapping("/{userId}/block")
    @Operation(
            summary = "Block user",
            description = "Blocks a user by setting status to BANNED. доступно только SUPER ADMIN"
    )
    public ResponseEntity<Void> blockUser(
            @Parameter(description = "User ID", required = true)
            @PathVariable UUID userId,
            @AuthenticationPrincipal User currentUser
    ) {
        userControlService.blockUser(userId, currentUser.getEmail());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}/unblock")
    @Operation(
            summary = "Unblock user",
            description = "Restores user access by setting status to ACTIVE. Only SUPER ADMIN"
    )
    public ResponseEntity<Void> unblockUser(
            @Parameter(description = "User ID", required = true)
            @PathVariable UUID userId,
            @AuthenticationPrincipal User currentUser
    ) {
        userControlService.unblockUser(userId, currentUser.getEmail());
        return ResponseEntity.noContent().build();
    }
}