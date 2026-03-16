package com.tramplin.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "favourites")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Favourite {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "opportunity_id", referencedColumnName = "id")
    private Opportunity opportunity;
}