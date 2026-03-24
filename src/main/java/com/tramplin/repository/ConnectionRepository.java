package com.tramplin.repository;

import com.tramplin.entity.Connection;
import com.tramplin.enums.ConnectionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ConnectionRepository extends JpaRepository<Connection, UUID> {

    @Query("""
            SELECT c FROM Connection c
            WHERE c.status = :status
            AND (c.requester.id = :seekerId OR c.receiver.id = :seekerId)
            AND (
                :search IS NULL OR :search = ''
                OR LOWER(c.requester.firstName) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(c.requester.lastName) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(c.receiver.firstName) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(c.receiver.lastName) LIKE LOWER(CONCAT('%', :search, '%'))
            )
            """)
    List<Connection> findConnectionsBySeeker(
            @Param("seekerId") UUID seekerId,
            @Param("status") ConnectionStatus status,
            @Param("search") String search);
}