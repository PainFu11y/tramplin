package com.tramplin.repository;

import com.tramplin.entity.CompanySphere;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompanySphereRepository extends JpaRepository<CompanySphere, UUID> {
}
