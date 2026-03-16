package com.tramplin.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "recommendations")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "from_seeker_id", referencedColumnName = "id")
    private Seeker fromSeeker;

    @ManyToOne
    @JoinColumn(name = "to_seeker_id", referencedColumnName = "id")
    private Seeker toSeeker;

    @ManyToOne
    @JoinColumn(name = "opportunity_id", referencedColumnName = "id")
    private Opportunity opportunity;
}