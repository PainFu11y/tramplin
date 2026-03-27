package com.tramplin.dto.admin.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class AdminResponse {

    private UUID id;
    private UUID userId;
    private String email;
}