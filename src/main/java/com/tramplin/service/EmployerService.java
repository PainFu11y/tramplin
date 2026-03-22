package com.tramplin.service;

import com.tramplin.dto.company.request.UpdateCompanyRequest;
import com.tramplin.dto.company.response.CompanyResponse;
import com.tramplin.entity.*;
import com.tramplin.exception.ResourceNotFoundException;
import com.tramplin.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployerService {

    private final EmployerRepository employerRepository;
    private final CompanyRepository companyRepository;
    private final CompanySphereRepository companySphereRepository;

    public CompanyResponse getProfile(User currentUser) {
        Employer employer = getEmployer(currentUser);
        return toResponse(employer.getCompany());
    }

    @Transactional
    public CompanyResponse updateProfile(User currentUser, UpdateCompanyRequest request) {
        Employer employer = getEmployer(currentUser);
        Company company = employer.getCompany();

        if (request.getDescription() != null) {
            company.setDescription(request.getDescription());
        }

        if (request.getWebsiteUrl() != null) {
            company.getSocials().removeIf(s -> s.getName().equals("website"));
            company.getSocials().add(CompanySocial.builder()
                    .company(company)
                    .name("website")
                    .value(request.getWebsiteUrl())
                    .build());
        }

        if (request.getSpheres() != null) {
            company.getSpheres().clear();
            List<CompanySphere> newSpheres = request.getSpheres().stream()
                    .map(name -> CompanySphere.builder()
                            .company(company)
                            .name(name)
                            .build())
                    .collect(Collectors.toList());
            company.getSpheres().addAll(newSpheres);
        }

        companyRepository.save(company);
        return toResponse(company);
    }

    private Employer getEmployer(User currentUser) {
        return employerRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Employer profile not found"));
    }

    private CompanyResponse toResponse(Company company) {
        return CompanyResponse.builder()
                .id(company.getId())
                .name(company.getName())
                .description(company.getDescription())
                .isVerified(company.getIsVerified())
                .socials(company.getSocials().stream()
                        .map(s -> s.getName() + ": " + s.getValue())
                        .collect(Collectors.toList()))
                .spheres(company.getSpheres().stream()
                        .map(CompanySphere::getName)
                        .collect(Collectors.toList()))
                .build();
    }
}