package com.tramplin.service;

import com.tramplin.dto.admin.response.UserResponse;
import com.tramplin.entity.User;
import com.tramplin.enums.UserStatus;
import com.tramplin.exception.ResourceNotFoundException;
import com.tramplin.exception.UnauthorizedException;
import com.tramplin.repository.AdminRepository;
import com.tramplin.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserControlService {

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

    public List<UserResponse> getUsers(String email, String currentAdminEmail) {
        adminRepository.findByUserEmailAndIsSuperAdminTrue(currentAdminEmail)
                .orElseThrow(() -> new UnauthorizedException("Only SUPER ADMIN can access this endpoint"));

        List<User> users;
        if (email == null || email.isBlank()) {
            users = userRepository.findAll();
        } else {
            users = userRepository.findByEmailContainingIgnoreCase(email);
        }

        return users.stream()
                .map(u -> UserResponse.builder()
                        .id(u.getId())
                        .email(u.getEmail())
                        .role(u.getRole())
                        .status(u.getStatus())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public void blockUser(UUID userId, String currentAdminEmail) {

        adminRepository.findByUserEmailAndIsSuperAdminTrue(currentAdminEmail)
                .orElseThrow(() -> new UnauthorizedException("Only SUPER ADMIN can block users"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getStatus() == UserStatus.BANNED) {
            throw new IllegalStateException("User is already blocked");
        }

        user.setStatus(UserStatus.BANNED);
        userRepository.save(user);
    }

    @Transactional
    public void unblockUser(UUID userId, String currentAdminEmail) {
        adminRepository.findByUserEmailAndIsSuperAdminTrue(currentAdminEmail)
                .orElseThrow(() -> new UnauthorizedException("Only SUPER ADMIN can unblock users"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getStatus() == UserStatus.ACTIVE) {
            throw new IllegalStateException("User is already active");
        }

        if (user.getEmail().equals(currentAdminEmail)) {
            throw new IllegalStateException("You cannot unblock yourself");
        }

        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
    }
}