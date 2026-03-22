package com.tramplin.dto.verification.request;

import com.tramplin.enums.VerificationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ReviewVerificationRequest {

    @NotNull
    private VerificationStatus status;
}