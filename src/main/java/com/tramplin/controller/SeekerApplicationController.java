package com.tramplin.controller;

import com.tramplin.dto.application.response.SeekerApplicationResponse;
import com.tramplin.service.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/seekers/applications")
@RequiredArgsConstructor
public class SeekerApplicationController {

    private final ApplicationService applicationService;

    @Operation(
            summary = "Get all applications of a seeker",
            description = "Returns the full list of applications submitted by a specific seeker without pagination."
    )
    @GetMapping("/{seekerId}")
    public ResponseEntity<?> getSeekerApplicationsList(
            @Parameter(description = "UUID of the seeker", required = true)
            @PathVariable UUID seekerId) {
        return ResponseEntity.ok(applicationService.getSeekerApplications(seekerId));
    }

    @Operation(
            summary = "Get paged applications of a seeker",
            description = "Returns a paginated list of applications submitted by a specific seeker. Supports pageable query parameters like page, size, and sort."
    )
    @GetMapping("/{seekerId}/paged")
    public ResponseEntity<Page<SeekerApplicationResponse>> getSeekerApplications(
            @Parameter(description = "UUID of the seeker", required = true)
            @PathVariable UUID seekerId,
            @Parameter(description = "Pageable object (page, size, sort)") Pageable pageable) {
        Page<SeekerApplicationResponse> page = applicationService.getSeekerApplications(seekerId, pageable);
        return ResponseEntity.ok(page);
    }
}