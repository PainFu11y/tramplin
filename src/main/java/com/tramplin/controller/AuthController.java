package com.tramplin.controller;

import com.tramplin.dto.employer.request.RegisterEmployerRequest;
import com.tramplin.dto.request.LoginRequest;
import com.tramplin.dto.request.RefreshTokenRequest;
import com.tramplin.dto.response.AuthResponse;
import com.tramplin.dto.seeker.request.RegisterSeekerRequest;
import com.tramplin.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Register, login and refresh token")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Register as a seeker")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Seeker registered successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "409", description = "Email already in use")
    })
    @PostMapping("/register/seeker")
    public ResponseEntity<AuthResponse> registerSeeker(@Valid @RequestBody RegisterSeekerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerSeeker(request));
    }

    @Operation(summary = "Register as an employer")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Employer registered successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "409", description = "Email or company already exists")
    })
    @PostMapping("/register/employer")
    public ResponseEntity<AuthResponse> registerEmployer(@Valid @RequestBody RegisterEmployerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerEmployer(request));
    }

    @Operation(summary = "Login with email and password")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(summary = "Refresh access token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Token refreshed"),
            @ApiResponse(responseCode = "401", description = "Invalid or expired refresh token")
    })
    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.refresh(request));
    }
}