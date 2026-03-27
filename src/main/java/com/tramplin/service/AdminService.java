package com.tramplin.service;

import com.tramplin.dto.admin.response.AdminResponse;
import com.tramplin.dto.employer.response.EmployerResponse;
import com.tramplin.dto.seeker.response.SeekerResponse;
import com.tramplin.entity.Admin;
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


    public AdminResponse getAdminProfile(UUID userId) {
        Admin admin = adminRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin profile not found"));

        return AdminResponse.builder()
                .id(admin.getId())
                .userId(admin.getUser().getId())
                .email(admin.getUser().getEmail())
                .build();
    }
}