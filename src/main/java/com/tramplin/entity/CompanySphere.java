package com.tramplin.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "companies_spheres")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CompanySphere {

    @Id
    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    @Column(length = 255)
    private String name;
}