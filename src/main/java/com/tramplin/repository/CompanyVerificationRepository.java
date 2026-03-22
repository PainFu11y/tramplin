package com.tramplin.repository;

import com.tramplin.entity.CompanyVerification;
import com.tramplin.enums.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompanyVerificationRepository extends JpaRepository<CompanyVerification, UUID> {
    List<CompanyVerification> findByStatus(VerificationStatus status);
    Optional<CompanyVerification> findByCompanyId(UUID companyId);
}