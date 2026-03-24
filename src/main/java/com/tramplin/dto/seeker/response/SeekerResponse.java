package com.tramplin.dto.seeker.response;

import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SeekerResponse {
    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private String university;
    private LocalDate graduationYear;
    private String course;
    private String portfolioUrl;
    private Boolean isProfilePrivate;
    private Boolean isApplicationsHistoryPrivate;
    private Boolean isRecommendationsPrivate;
}