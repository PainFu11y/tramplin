package com.tramplin.dto.admin.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateAdminRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private Boolean isSuperAdmin = false;
}