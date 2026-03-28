package com.tramplin.controller;

import com.tramplin.dto.request.UpdatePasswordRequest;
import com.tramplin.entity.User;
import com.tramplin.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/me/password")
    @Operation(summary = "Update current user password")
    public ResponseEntity<Void> updatePassword(
            @AuthenticationPrincipal User currentUser,
            @Valid @RequestBody UpdatePasswordRequest request
    ) {
        userService.updatePassword(currentUser, request);
        return ResponseEntity.noContent().build();
    }
}