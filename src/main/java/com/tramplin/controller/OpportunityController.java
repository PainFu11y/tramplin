package com.tramplin.controller;

import com.tramplin.dto.opportunity.request.CreateOpportunityRequest;
import com.tramplin.dto.opportunity.request.UpdateOpportunityRequest;
import com.tramplin.dto.response.OpportunityResponse;
import com.tramplin.entity.User;
import com.tramplin.enums.WorkFormat;
import com.tramplin.service.OpportunityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/opportunities")
@RequiredArgsConstructor
@Tag(name = "Opportunities")
public class OpportunityController {

    private final OpportunityService opportunityService;

    @GetMapping
    public ResponseEntity<Page<OpportunityResponse>> getOpportunities(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) WorkFormat workFormat,
            @RequestParam(required = false) List<String> tags,
            @RequestParam(required = false) Integer salaryFrom,
            @RequestParam(required = false) Integer salaryTo,
            Pageable pageable) {

        return ResponseEntity.ok(
                opportunityService.getOpportunities(
                        search,
                        workFormat,
                        tags,
                        salaryFrom,
                        salaryTo,
                        pageable
                )
        );
    }

    @Operation(summary = "Create a new opportunity")
    @PostMapping
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<OpportunityResponse> create(
            @AuthenticationPrincipal User currentUser,
            @Valid @RequestBody CreateOpportunityRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(opportunityService.create(currentUser, request));
    }

    @Operation(summary = "Update an opportunity")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<OpportunityResponse> update(
            @AuthenticationPrincipal User currentUser,
            @PathVariable UUID id,
            @RequestBody UpdateOpportunityRequest request) {
        return ResponseEntity.ok(opportunityService.update(currentUser, id, request));
    }

    @Operation(summary = "Archive an opportunity")
    @PutMapping("/{id}/archive")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<OpportunityResponse> archive(
            @AuthenticationPrincipal User currentUser,
            @PathVariable UUID id) {
        return ResponseEntity.ok(opportunityService.archive(currentUser, id));
    }

    @Operation(summary = "Get company opportunities with search")
    @GetMapping("/company/{companyId}")
    public ResponseEntity<Page<OpportunityResponse>> getCompanyOpportunities(
            @PathVariable UUID companyId,
            @RequestParam(required = false) String search,
            @PageableDefault(size = 20, sort = "creationDate") Pageable pageable) {
        return ResponseEntity.ok(opportunityService.getCompanyOpportunities(
                companyId, search, pageable));
    }
}