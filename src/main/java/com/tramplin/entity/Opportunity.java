package com.tramplin.entity;

import com.tramplin.enums.*;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "opportunities")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Opportunity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private OpportunityType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "work_format")
    private WorkFormat workFormat;

    @Enumerated(EnumType.STRING)
    @Column(name = "employment_type")
    private EmploymentType employmentType;

    @Enumerated(EnumType.STRING)
    private Level level;

    private Integer salary;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String address;

    @Column(precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "event_date")
    private LocalDate eventDate;

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private Employer createdBy;

    @Enumerated(EnumType.STRING)
    private OpportunityStatus status;

    @ManyToMany
    @JoinTable(
            name = "opportunity_tags",
            joinColumns = @JoinColumn(name = "opportunity_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;
}