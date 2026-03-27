package com.tramplin.controller;

import com.tramplin.dto.admin.request.CreateAdminRequest;
import com.tramplin.dto.admin.response.AdminResponse;
import com.tramplin.dto.admin.response.CreateAdminResponse;
import com.tramplin.entity.User;
import com.tramplin.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/profile")
    @Operation(summary = "Get admin profile", description = "Returns the profile of the currently logged-in admin")
    public AdminResponse getAdminProfile(@RequestParam UUID userId) {
        return adminService.getAdminProfile(userId);
    }

    @GetMapping("/moderators")
    @Operation(summary = "Get all moderators", description = "Returns a list of all moderators (accessible only by super admin)")
    public List<AdminResponse> getAllModerators(@RequestParam UUID superAdminUserId) {
        return adminService.getAllModerators(superAdminUserId);
    }
    @PostMapping
    @Operation(summary = "Create new moderator (only email and password required)")
    public CreateAdminResponse createModerator(
            @Valid @RequestBody CreateAdminRequest request,
            @AuthenticationPrincipal User currentUser) {
        return adminService.createModerator(request, currentUser);
    }
}