package com.tramplin.service;

import com.tramplin.dto.employer.response.EmployerResponse;
import com.tramplin.dto.seeker.response.SeekerResponse;
import com.tramplin.entity.Seeker;
import com.tramplin.entity.Employer;
import com.tramplin.entity.User;
import com.tramplin.exception.ResourceNotFoundException;
import com.tramplin.repository.AdminRepository;
import com.tramplin.repository.SeekerRepository;
import com.tramplin.repository.EmployerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final SeekerRepository seekerRepository;
    private final EmployerRepository employerRepository;


    public Object getUserProfile(UUID userId) {
        User user = adminRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Seeker seeker = seekerRepository.findByUserId(userId).orElse(null);
        if (seeker != null) {
            return SeekerResponse.builder()
                    .id(seeker.getId())
                    .firstName(seeker.getFirstName())
                    .lastName(seeker.getLastName())
                    .university(seeker.getUniversity())
                    .graduationYear(seeker.getGraduationYear())
                    .course(seeker.getCourse())
                    .portfolioUrl(seeker.getPortfolioUrl())
                    .isPrivateProfile(seeker.getIsPrivateProfile())
                    .isApplicationsHistoryPrivate(seeker.getIsApplicationsHistoryPrivate())
                    .isRecommendationsPrivate(seeker.getIsRecommendationsPrivate())
                    .build();
        }

        Employer employer = employerRepository.findByUserId(userId).orElse(null);
        if (employer != null) {
            return EmployerResponse.builder()
                    .id(employer.getId())
                    .companyId(employer.getCompany().getId())
                    .companyName(employer.getCompany().getName())
                    .companyVerified(employer.getCompany().getIsVerified())
                    .build();
        }

        throw new ResourceNotFoundException("Profile not found for this user");
    }
}