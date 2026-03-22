package com.tramplin.controller;

import com.tramplin.dto.company.request.UpdateCompanyRequest;
import com.tramplin.dto.company.response.CompanyResponse;
import com.tramplin.entity.User;
import com.tramplin.service.EmployerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employer/profile")
@RequiredArgsConstructor
@Tag(name = "Employer Profile")
@PreAuthorize("hasRole('EMPLOYER')")
public class EmployerController {

    private final EmployerService employerService;

    @Operation(summary = "Get employer company profile")
    @GetMapping
    public ResponseEntity<CompanyResponse> getProfile(
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(employerService.getProfile(currentUser));
    }

    @Operation(summary = "Update employer company profile")
    @PutMapping
    public ResponseEntity<CompanyResponse> updateProfile(
            @AuthenticationPrincipal User currentUser,
            @RequestBody UpdateCompanyRequest request) {
        return ResponseEntity.ok(employerService.updateProfile(currentUser, request));
    }
}