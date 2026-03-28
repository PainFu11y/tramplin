package com.tramplin.controller;

import com.tramplin.dto.seeker.request.UpdateSeekerRequest;
import com.tramplin.dto.seeker.response.SeekerResponse;
import com.tramplin.entity.User;
import com.tramplin.service.SeekerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/seekers")
@RequiredArgsConstructor
public class SeekerController {

    private final SeekerService seekerService;

    @GetMapping("/me")
    @Operation(summary = "Get current seeker profile")
    public SeekerResponse getMyProfile(
            @AuthenticationPrincipal User currentUser
    ) {
        return seekerService.getProfile(currentUser);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get seeker profile by ID")
    public SeekerResponse getSeekerById(
            @Parameter(description = "Seeker ID", required = true)
            @PathVariable UUID id
    ) {
        return seekerService.getSeekerById(id);
    }

    @PutMapping("/me")
    @Operation(summary = "Update current seeker profile")
    public SeekerResponse updateProfile(
            @AuthenticationPrincipal User currentUser,
            @RequestBody UpdateSeekerRequest request
    ) {
        return seekerService.updateProfile(currentUser, request);
    }
}