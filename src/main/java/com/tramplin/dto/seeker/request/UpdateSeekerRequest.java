package com.tramplin.dto.seeker.request;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateSeekerRequest {
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