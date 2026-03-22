package com.tramplin.service;

import com.tramplin.dto.favourite.response.FavouriteResponse;
import com.tramplin.dto.response.OpportunityResponse;
import com.tramplin.entity.Favourite;
import com.tramplin.entity.Opportunity;
import com.tramplin.entity.Tag;
import com.tramplin.entity.User;
import com.tramplin.exception.ResourceNotFoundException;
import com.tramplin.repository.FavouriteRepository;
import com.tramplin.repository.OpportunityRepository;
import com.tramplin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavouriteService {

    private final FavouriteRepository favouriteRepository;
    private final OpportunityRepository opportunityRepository;
    private final UserRepository userRepository;

    public List<OpportunityResponse> getFavourites(UUID userId) {
        return favouriteRepository.findByUserId(userId)
                .stream()
                .map(f -> toOpportunityResponse(f.getOpportunity()))
                .collect(Collectors.toList());
    }

    @Transactional
    public FavouriteResponse toggleFavourite(UUID userId, UUID opportunityId) {
        Opportunity opportunity = opportunityRepository.findById(opportunityId)
                .orElseThrow(() -> new ResourceNotFoundException("Opportunity not found"));

        Optional<Favourite> existing = favouriteRepository
                .findByUserIdAndOpportunityId(userId, opportunityId);

        if (existing.isPresent()) {
            favouriteRepository.delete(existing.get());
            return FavouriteResponse.builder()
                    .opportunityId(opportunityId)
                    .isFavourite(false)
                    .build();
        } else {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            favouriteRepository.save(Favourite.builder()
                    .user(user)
                    .opportunity(opportunity)
                    .build());

            return FavouriteResponse.builder()
                    .opportunityId(opportunityId)
                    .isFavourite(true)
                    .build();
        }
    }

    private OpportunityResponse toOpportunityResponse(Opportunity o) {
        return OpportunityResponse.builder()
                .id(o.getId())
                .title(o.getTitle())
                .description(o.getDescription())
                .type(o.getType())
                .workFormat(o.getWorkFormat())
                .employmentType(o.getEmploymentType())
                .level(o.getLevel())
                .salary(o.getSalary())
                .companyName(o.getCompany().getName())
                .companyVerified(o.getCompany().getIsVerified())
                .city(o.getCity())
                .address(o.getAddress())
                .latitude(o.getLatitude())
                .longitude(o.getLongitude())
                .creationDate(o.getCreationDate())
                .expirationDate(o.getExpirationDate())
                .eventDate(o.getEventDate())
                .status(o.getStatus())
                .tags(o.getTags().stream().map(Tag::getName).collect(Collectors.toList()))
                .build();
    }
}