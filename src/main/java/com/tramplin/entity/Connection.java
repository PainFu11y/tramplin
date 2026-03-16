package com.tramplin.entity;

import com.tramplin.enums.ConnectionStatus;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "connections")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Connection {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "requester_id", referencedColumnName = "id")
    private Seeker requester;

    @ManyToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "id")
    private Seeker receiver;

    @Enumerated(EnumType.STRING)
    private ConnectionStatus status;
}