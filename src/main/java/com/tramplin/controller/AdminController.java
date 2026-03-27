package com.tramplin.controller;

import com.tramplin.dto.admin.response.AdminResponse;
import com.tramplin.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
}