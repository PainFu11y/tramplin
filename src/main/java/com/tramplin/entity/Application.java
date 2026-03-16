package com.tramplin.entity;

import com.tramplin.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "applications")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "opportunity_id", referencedColumnName = "id")
    private Opportunity opportunity;

    @ManyToOne
    @JoinColumn(name = "seeker_id", referencedColumnName = "id")
    private Seeker seeker;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Column(name = "cover_letter", columnDefinition = "TEXT")
    private String coverLetter;
}