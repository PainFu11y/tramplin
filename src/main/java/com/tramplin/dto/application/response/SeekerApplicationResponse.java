package com.tramplin.dto.application.response;

import com.tramplin.enums.ApplicationStatus;
import lombok.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeekerApplicationResponse {

    private UUID applicationId;
    private UUID opportunityId;
    private String opportunityTitle;
    private ApplicationStatus status;
    private String coverLetter;
    private String companyName;
}