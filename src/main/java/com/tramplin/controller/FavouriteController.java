package com.tramplin.controller;

import com.tramplin.dto.favourite.response.FavouriteResponse;
import com.tramplin.dto.response.OpportunityResponse;
import com.tramplin.entity.User;
import com.tramplin.service.FavouriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/favourites")
@RequiredArgsConstructor
@Tag(name = "Favourites")
public class FavouriteController {

    private final FavouriteService favouriteService;

    @Operation(summary = "Get all favourite opportunities for current user")
    @GetMapping
    public ResponseEntity<List<OpportunityResponse>> getFavourites(
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(favouriteService.getFavourites(currentUser.getId()));
    }

    @Operation(summary = "Toggle favourite state for an opportunity (add/remove)")
    @PutMapping("/{opportunityId}")
    public ResponseEntity<FavouriteResponse> toggleFavourite(
            @AuthenticationPrincipal User currentUser,
            @PathVariable UUID opportunityId) {
        return ResponseEntity.ok(favouriteService.toggleFavourite(currentUser.getId(), opportunityId));
    }
}