package com.tramplin.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "seekers")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Seeker {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "first_name", length = 255)
    private String firstName;

    @Column(name = "last_name", length = 255)
    private String lastName;

    @Column(length = 255)
    private String university;

    @Column(name = "graduation_year")
    private LocalDate graduationYear;

    @Column(length = 255)
    private String course;

    @Column(name = "portfolio_url")
    private String portfolioUrl;

    @Column(name = "is_private_profile")
    private Boolean isPrivateProfile;

    @Column(name = "is_applications_history_private")
    @Builder.Default
    private Boolean isApplicationsHistoryPrivate = false;

    @Column(name = "is_recomendations_private")
    @Builder.Default
    private Boolean isRecommendationsPrivate = false;
}