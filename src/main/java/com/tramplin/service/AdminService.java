package com.tramplin.service;

import com.tramplin.dto.admin.request.CreateAdminRequest;
import com.tramplin.dto.admin.response.AdminResponse;
import com.tramplin.dto.admin.response.CreateAdminResponse;
import com.tramplin.entity.Admin;
import com.tramplin.entity.User;
import com.tramplin.enums.Role;
import com.tramplin.enums.UserStatus;
import com.tramplin.exception.ResourceNotFoundException;
import com.tramplin.exception.UnauthorizedException;
import com.tramplin.repository.AdminRepository;
import com.tramplin.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


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

    @Transactional
    public CreateAdminResponse createModerator(CreateAdminRequest request, User currentUser) {
        Admin currentAdmin = adminRepository.findByUserEmail(currentUser.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Current user is not an admin"));

        if (!Boolean.TRUE.equals(currentAdmin.getIsSuperAdmin())) {
            throw new UnauthorizedException("Only SUPER ADMIN can create moderators");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .status(UserStatus.ACTIVE)
                .build();

        userRepository.save(user);

        Admin admin = Admin.builder()
                .user(user)
                .isSuperAdmin(false)
                .build();

        adminRepository.save(admin);

        return CreateAdminResponse.builder()
                .id(admin.getId())
                .email(user.getEmail())
                .isSuperAdmin(admin.getIsSuperAdmin())
                .build();
    }
}