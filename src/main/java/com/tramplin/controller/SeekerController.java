package com.tramplin.controller;

import com.tramplin.dto.seeker.response.SeekerResponse;
import com.tramplin.entity.User;
import com.tramplin.service.SeekerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/seekers")
@RequiredArgsConstructor
public class SeekerController {

    private final SeekerService seekerService;

    @GetMapping("/{id}")
    public ResponseEntity<SeekerResponse> getSeekerById(
            @PathVariable UUID id,
            @AuthenticationPrincipal User currentUser) {

        return ResponseEntity.ok(
                seekerService.getSeekerById(id)
        );


    }
}