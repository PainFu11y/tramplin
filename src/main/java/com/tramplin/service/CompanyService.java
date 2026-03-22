package com.tramplin.service;

import com.tramplin.dto.company.response.CompanyResponse;
import com.tramplin.entity.Company;
import com.tramplin.exception.ResourceNotFoundException;
import com.tramplin.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyResponse getByName(String name) {
        Company company = companyRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found: " + name));

        return toResponse(company);
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
                        .map(s -> s.getName())
                        .collect(Collectors.toList()))
                .build();
    }
}