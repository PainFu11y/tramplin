package com.tramplin.controller;

import com.tramplin.dto.response.OpportunityResponse;
import com.tramplin.enums.WorkFormat;
import com.tramplin.service.OpportunityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/opportunities")
@RequiredArgsConstructor
@Tag(name = "Opportunities")
public class OpportunityController {

    private final OpportunityService opportunityService;

    @Operation(summary = "Get opportunities with filters")
    @GetMapping
    public ResponseEntity<Page<OpportunityResponse>> getOpportunities(
            @RequestParam(required = false) WorkFormat workFormat,
            @RequestParam(required = false) List<String> tags,
            @RequestParam(required = false) Integer salaryFrom,
            @RequestParam(required = false) Integer salaryTo,
            @PageableDefault(size = 20, sort = "creationDate") Pageable pageable) {

        return ResponseEntity.ok(opportunityService.getOpportunities(
                workFormat, tags, salaryFrom, salaryTo, pageable));
    }
}