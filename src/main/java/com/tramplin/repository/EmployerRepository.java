package com.tramplin.repository;

import com.tramplin.entity.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface EmployerRepository extends JpaRepository<Employer, UUID> {}