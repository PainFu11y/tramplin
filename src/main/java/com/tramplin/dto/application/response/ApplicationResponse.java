package com.tramplin.dto.application.response;

import com.tramplin.enums.ApplicationStatus;
import lombok.*;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ApplicationResponse {
    private UUID id;
    private UUID opportunityId;
    private String opportunityTitle;
    private UUID seekerId;
    private String seekerFirstName;
    private String seekerLastName;
    private String seekerEmail;
    private String coverLetter;
    private ApplicationStatus status;
}