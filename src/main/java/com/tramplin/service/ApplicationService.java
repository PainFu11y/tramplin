package com.tramplin.service;

import com.tramplin.dto.application.request.UpdateApplicationStatusRequest;
import com.tramplin.dto.application.response.ApplicationResponse;
import com.tramplin.dto.application.response.SeekerApplicationResponse;
import com.tramplin.entity.Application;
import com.tramplin.entity.Employer;
import com.tramplin.entity.User;
import com.tramplin.exception.ResourceNotFoundException;
import com.tramplin.repository.ApplicationRepository;
import com.tramplin.repository.EmployerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final EmployerRepository employerRepository;

    public List<ApplicationResponse> getCompanyApplications(User currentUser, UUID opportunityId) {
        Employer employer = getEmployer(currentUser);

        List<Application> applications = opportunityId != null
                ? applicationRepository.findByOpportunityId(opportunityId)
                : applicationRepository.findByOpportunityCompanyId(employer.getCompany().getId());

        return applications.stream()
                .filter(a -> a.getOpportunity().getCompany().getId()
                        .equals(employer.getCompany().getId()))
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ApplicationResponse updateStatus(
            User currentUser,
            UUID applicationId,
            UpdateApplicationStatusRequest request) {

        Employer employer = getEmployer(currentUser);

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        if (!application.getOpportunity().getCompany().getId()
                .equals(employer.getCompany().getId())) {
            throw new IllegalStateException("You don't have permission to update this application");
        }

        application.setStatus(request.getStatus());
        applicationRepository.save(application);

        return toResponse(application);
    }

    public List<SeekerApplicationResponse> getSeekerApplications(UUID seekerId) {
        List<Application> applications = applicationRepository.findBySeekerId(seekerId);

        return applications.stream()
                .map(a -> SeekerApplicationResponse.builder()
                        .applicationId(a.getId())
                        .opportunityId(a.getOpportunity().getId())
                        .opportunityTitle(a.getOpportunity().getTitle())
                        .status(a.getStatus())
                        .coverLetter(a.getCoverLetter())
                        .companyName(a.getOpportunity().getCompany().getName())
                        .build())
                .collect(Collectors.toList());
    }

    public Page<SeekerApplicationResponse> getSeekerApplications(UUID seekerId, Pageable pageable) {
        return applicationRepository.findBySeekerId(seekerId, pageable)
                .map(a -> SeekerApplicationResponse.builder()
                        .applicationId(a.getId())
                        .opportunityId(a.getOpportunity().getId())
                        .opportunityTitle(a.getOpportunity().getTitle())
                        .status(a.getStatus())
                        .coverLetter(a.getCoverLetter())
                        .companyName(a.getOpportunity().getCompany().getName())
                        .build());
    }

    private Employer getEmployer(User currentUser) {
        return employerRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Employer profile not found"));
    }

    private ApplicationResponse toResponse(Application a) {
        return ApplicationResponse.builder()
                .id(a.getId())
                .opportunityId(a.getOpportunity().getId())
                .opportunityTitle(a.getOpportunity().getTitle())
                .seekerId(a.getSeeker().getId())
                .seekerFirstName(a.getSeeker().getFirstName())
                .seekerLastName(a.getSeeker().getLastName())
                .seekerEmail(a.getSeeker().getUser().getEmail())
                .coverLetter(a.getCoverLetter())
                .status(a.getStatus())
                .build();
    }
}