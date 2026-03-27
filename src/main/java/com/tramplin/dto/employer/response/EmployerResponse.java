package com.tramplin.dto.employer.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class EmployerResponse {
    private UUID id;
    private UUID companyId;
    private String companyName;
    private Boolean companyVerified;
}