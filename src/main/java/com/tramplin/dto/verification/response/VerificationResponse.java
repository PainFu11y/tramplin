package com.tramplin.dto.verification.response;

import com.tramplin.enums.VerificationStatus;
import lombok.*;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class VerificationResponse {
    private UUID id;
    private UUID companyId;
    private String companyName;
    private String taxNumber;
    private String submittedByEmail;
    private VerificationStatus status;
}