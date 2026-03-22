package com.tramplin.repository;

import com.tramplin.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
    boolean existsByName(String name);
    Optional<Company> findByName(String name);
}