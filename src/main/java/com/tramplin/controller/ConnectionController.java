package com.tramplin.controller;

import com.tramplin.dto.connection.ConnectionResponse;
import com.tramplin.dto.seeker.response.SeekerResponse;
import com.tramplin.entity.User;
import com.tramplin.service.ConnectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/seeker/connections")
@RequiredArgsConstructor
@Tag(name = "Seeker - Connections")
@PreAuthorize("hasRole('SEEKER')")
public class ConnectionController {

    private final ConnectionService connectionService;

    @Operation(summary = "Get connections list with optional search by name")
    @GetMapping
    public ResponseEntity<List<ConnectionResponse>> getConnections(
            @AuthenticationPrincipal User currentUser,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(connectionService.getMyConnections(currentUser, search));
    }

    @Operation(summary = "Get connections of another user with optional search")
    @GetMapping("/user/{seekerId}")
    public ResponseEntity<List<ConnectionResponse>> getConnectionsForUser(
            @PathVariable UUID seekerId,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(connectionService.getConnectionsForSeeker(seekerId, search));
    }

    @Operation(summary = "People you may know")
    @GetMapping("/possible")
    public ResponseEntity<List<SeekerResponse>> getPeopleYouMayKnow(
            @AuthenticationPrincipal User currentUser) {

        return ResponseEntity.ok(connectionService.getPeopleYouMayKnow(currentUser));
    }
}