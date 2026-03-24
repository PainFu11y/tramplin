package com.tramplin.controller;


import com.tramplin.dto.application.request.UpdateApplicationStatusRequest;
import com.tramplin.dto.application.response.ApplicationResponse;
import com.tramplin.entity.User;
import com.tramplin.service.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/employer/applications")
@RequiredArgsConstructor
@Tag(name = "Employer - Applications")
@PreAuthorize("hasRole('EMPLOYER')")
public class ApplicationServiceController {
    private final ApplicationService applicationService;


    @Operation(summary = "Get all applications for company, optionally filtered by opportunity")
    @GetMapping
    public ResponseEntity<List<ApplicationResponse>> getCompanyApplications(
            @AuthenticationPrincipal User currentUser,
            @RequestParam(required = false) UUID opportunityId)
    {
    return ResponseEntity.ok(applicationService.getCompanyApplications(currentUser, opportunityId));
    }

    @Operation(summary = "Update application status")
    @PutMapping("/{applicationId}/status")
    public ResponseEntity<ApplicationResponse> updateStatus(
            @AuthenticationPrincipal User currentUser,
            @PathVariable UUID applicationId,
            @Valid @RequestBody UpdateApplicationStatusRequest request){
        return ResponseEntity.ok(applicationService.updateStatus(currentUser, applicationId, request));
    }



}
