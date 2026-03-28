package com.tramplin.service;

import com.tramplin.dto.seeker.request.UpdateSeekerRequest;
import com.tramplin.dto.seeker.response.SeekerResponse;
import com.tramplin.entity.Seeker;
import com.tramplin.entity.User;
import com.tramplin.exception.ResourceNotFoundException;
import com.tramplin.repository.SeekerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SeekerService {

    private final SeekerRepository seekerRepository;

    public SeekerResponse getProfile(User currentUser) {
        Seeker seeker = getSeeker(currentUser);
        return toResponse(seeker);
    }

    public SeekerResponse getSeekerById(UUID id) {

        Seeker seeker = seekerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seeker not found"));

        return toResponse(seeker);
    }

    @Transactional
    public SeekerResponse updateProfile(User currentUser, UpdateSeekerRequest request) {
        Seeker seeker = getSeeker(currentUser);

        if (request.getFirstName() != null) seeker.setFirstName(request.getFirstName());
        if (request.getLastName() != null) seeker.setLastName(request.getLastName());
        if (request.getUniversity() != null) seeker.setUniversity(request.getUniversity());
        if (request.getGraduationYear() != null) seeker.setGraduationYear(request.getGraduationYear());
        if (request.getCourse() != null) seeker.setCourse(request.getCourse());
        if (request.getPortfolioUrl() != null) seeker.setPortfolioUrl(request.getPortfolioUrl());
        if (request.getIsProfilePrivate() != null) seeker.setIsPrivateProfile(request.getIsProfilePrivate());
        if (request.getIsApplicationsHistoryPrivate() != null) seeker.setIsApplicationsHistoryPrivate(request.getIsApplicationsHistoryPrivate());
        if (request.getIsRecommendationsPrivate() != null) seeker.setIsRecommendationsPrivate(request.getIsRecommendationsPrivate());

        seekerRepository.save(seeker);
        return toResponse(seeker);
    }

    private Seeker getSeeker(User currentUser) {
        return seekerRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Seeker profile not found"));
    }

    private SeekerResponse toResponse(Seeker s) {
        return SeekerResponse.builder()
                .id(s.getId())
                .email(s.getUser().getEmail())
                .firstName(s.getFirstName())
                .lastName(s.getLastName())
                .university(s.getUniversity())
                .graduationYear(s.getGraduationYear())
                .course(s.getCourse())
                .portfolioUrl(s.getPortfolioUrl())
                .isPrivateProfile(s.getIsPrivateProfile())
                .isApplicationsHistoryPrivate(s.getIsApplicationsHistoryPrivate())
                .isRecommendationsPrivate(s.getIsRecommendationsPrivate())
                .build();
    }
}