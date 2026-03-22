package com.tramplin.repository;

import com.tramplin.entity.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FavouriteRepository extends JpaRepository<Favourite, UUID> {
    List<Favourite> findByUserId(UUID userId);
    Optional<Favourite> findByUserIdAndOpportunityId(UUID userId, UUID opportunityId);
    boolean existsByUserIdAndOpportunityId(UUID userId, UUID opportunityId);
}