package com.tramplin.service;

import com.tramplin.dto.admin.response.AdminResponse;
import com.tramplin.entity.Admin;
import com.tramplin.exception.ResourceNotFoundException;
import com.tramplin.exception.UnauthorizedException;
import com.tramplin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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


    public List<AdminResponse> getAllModerators(UUID superAdminUserId) {
        Admin superAdmin = adminRepository.findByUserId(superAdminUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin profile not found"));

        if (!Boolean.TRUE.equals(superAdmin.getIsSuperAdmin())) {
            throw new UnauthorizedException("Only super admin can access moderators list");
        }

        List<Admin> moderators = adminRepository.findAllByIsSuperAdminFalse();

        return moderators.stream()
                .map(admin -> AdminResponse.builder()
                        .id(admin.getId())
                        .userId(admin.getUser().getId())
                        .email(admin.getUser().getEmail())
                        .build())
                .collect(Collectors.toList());
    }
}