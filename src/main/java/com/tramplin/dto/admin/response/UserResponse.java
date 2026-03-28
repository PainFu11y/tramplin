package com.tramplin.dto.admin.response;

import com.tramplin.enums.Role;
import com.tramplin.enums.UserStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class UserResponse {
    private UUID id;
    private String email;
    private Role role;
    private UserStatus status;
}