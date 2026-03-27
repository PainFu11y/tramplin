package com.tramplin.repository;

import com.tramplin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);
}