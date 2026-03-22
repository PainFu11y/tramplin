package com.tramplin.repository;

import com.tramplin.entity.Opportunity;
import com.tramplin.enums.OpportunityStatus;
import com.tramplin.enums.WorkFormat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.UUID;

public interface OpportunityRepository extends JpaRepository<Opportunity, UUID>,
        JpaSpecificationExecutor<Opportunity> {
}