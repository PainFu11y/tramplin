package com.tramplin.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "companies_socials")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CompanySocial {

    @Id
    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    @Column(length = 255)
    private String name;

    @Column(length = 255)
    private String value;
}