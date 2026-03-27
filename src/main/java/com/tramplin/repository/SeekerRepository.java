package com.tramplin.repository;

import com.tramplin.entity.Seeker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SeekerRepository extends JpaRepository<Seeker, UUID> {
    Optional<Seeker> findByUserId(UUID id);
    @Query("""
    SELECT s FROM Seeker s
    WHERE s.id != :currentSeekerId
    AND NOT EXISTS (
        SELECT c FROM Connection c
        WHERE 
            (c.requester.id = :currentSeekerId AND c.receiver.id = s.id)
            OR
            (c.receiver.id = :currentSeekerId AND c.requester.id = s.id)
    )
""")
    List<Seeker> findPossibleConnections(UUID currentSeekerId);
}