package com.tramplin.repository;

import com.tramplin.entity.Seeker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SeekerRepository extends JpaRepository<Seeker, UUID> {
    Optional<Seeker> findByUserId(UUID id);
}