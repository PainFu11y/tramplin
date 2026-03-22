package com.tramplin.service;

import com.tramplin.dto.verification.request.ReviewVerificationRequest;
import com.tramplin.dto.verification.response.VerificationResponse;
import com.tramplin.entity.CompanyVerification;
import com.tramplin.enums.VerificationStatus;
import com.tramplin.exception.ResourceNotFoundException;
import com.tramplin.repository.CompanyRepository;
import com.tramplin.repository.CompanyVerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyVerificationService {

    private final CompanyVerificationRepository verificationRepository;
    private final CompanyRepository companyRepository;

    public List<VerificationResponse> getPending() {
        return verificationRepository.findByStatus(VerificationStatus.PENDING)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<VerificationResponse> getAll() {
        return verificationRepository.findAll()
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public VerificationResponse review(UUID verificationId, ReviewVerificationRequest request) {
        CompanyVerification verification = verificationRepository.findById(verificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Verification not found"));

        if (verification.getStatus() != VerificationStatus.PENDING) {
            throw new IllegalStateException("Verification already reviewed");
        }

        verification.setStatus(request.getStatus());

        if (request.getStatus() == VerificationStatus.APPROVED) {
            verification.getCompany().setIsVerified(true);
            companyRepository.save(verification.getCompany());
        }

        verificationRepository.save(verification);
        return toResponse(verification);
    }

    public VerificationResponse getByCompanyId(UUID companyId) {
        return verificationRepository.findByCompanyId(companyId)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Verification not found for company"));
    }

    private VerificationResponse toResponse(CompanyVerification v) {
        return VerificationResponse.builder()
                .id(v.getId())
                .companyId(v.getCompany().getId())
                .companyName(v.getCompany().getName())
                .taxNumber(v.getTaxNumber().toString())
                .submittedByEmail(v.getSubmittedBy().getEmail())
                .status(v.getStatus())
                .build();
    }
}