package com.tramplin.controller;

import com.tramplin.dto.verification.request.ReviewVerificationRequest;
import com.tramplin.dto.verification.response.VerificationResponse;
import com.tramplin.service.CompanyVerificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/verifications")
@RequiredArgsConstructor
@Tag(name = "Admin - Company Verifications")
@PreAuthorize("hasRole('ADMIN')")
public class AdminVerificationController {

    private final CompanyVerificationService verificationService;

    @Operation(summary = "Get all pending verifications")
    @GetMapping("/pending")
    public ResponseEntity<List<VerificationResponse>> getPending() {
        return ResponseEntity.ok(verificationService.getPending());
    }

    @Operation(summary = "Get all verifications")
    @GetMapping
    public ResponseEntity<List<VerificationResponse>> getAll() {
        return ResponseEntity.ok(verificationService.getAll());
    }

    @Operation(summary = "Approve or reject a company verification")
    @PutMapping("/{id}/review")
    public ResponseEntity<VerificationResponse> review(
            @PathVariable UUID id,
            @Valid @RequestBody ReviewVerificationRequest request) {
        return ResponseEntity.ok(verificationService.review(id, request));
    }
}