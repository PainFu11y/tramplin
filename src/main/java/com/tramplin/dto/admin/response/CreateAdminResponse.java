package com.tramplin.dto.admin.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class CreateAdminResponse {
    private UUID id;
    private String email;
    private Boolean isSuperAdmin;
}
