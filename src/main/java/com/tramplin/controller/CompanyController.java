package com.tramplin.controller;

import com.tramplin.dto.company.response.CompanyResponse;
import com.tramplin.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
@Tag(name = "Companies")
public class CompanyController {

    private final CompanyService companyService;

    @Operation(summary = "Get company by name")
    @GetMapping("/{name}")
    public ResponseEntity<CompanyResponse> getByName(@PathVariable String name) {
        return ResponseEntity.ok(companyService.getByName(name));
    }
}