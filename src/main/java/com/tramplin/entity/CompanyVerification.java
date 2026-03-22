package com.tramplin.entity;

import com.tramplin.enums.VerificationStatus;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "company_verifications")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CompanyVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "submitted_by", referencedColumnName = "id")
    private User submittedBy;

    @Column(name = "tax_number")
    private Integer taxNumber;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private VerificationStatus status = VerificationStatus.PENDING;
}