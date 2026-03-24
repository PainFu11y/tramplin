package com.tramplin.service;

import com.tramplin.dto.connection.ConnectionResponse;
import com.tramplin.entity.Connection;
import com.tramplin.entity.Seeker;
import com.tramplin.entity.User;
import com.tramplin.enums.ConnectionStatus;
import com.tramplin.exception.ResourceNotFoundException;
import com.tramplin.repository.ConnectionRepository;
import com.tramplin.repository.SeekerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConnectionService {

    private final ConnectionRepository connectionRepository;
    private final SeekerRepository seekerRepository;

    public List<ConnectionResponse> getConnections(User currentUser, String search) {
        Seeker currentSeeker = seekerRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Seeker profile not found"));

        return connectionRepository
                .findConnectionsBySeeker(currentSeeker.getId(), ConnectionStatus.ACCEPTED, search)
                .stream()
                .map(c -> toResponse(c, currentSeeker.getId()))
                .collect(Collectors.toList());
    }

    private ConnectionResponse toResponse(Connection c, UUID currentSeekerId) {
        boolean isRequester = c.getRequester().getId().equals(currentSeekerId);
        Seeker other = isRequester ? c.getReceiver() : c.getRequester();

        return ConnectionResponse.builder()
                .id(c.getId())
                .seekerId(other.getId())
                .firstName(other.getFirstName())
                .lastName(other.getLastName())
                .university(other.getUniversity())
                .course(other.getCourse())
                .portfolioUrl(other.getIsPrivateProfile() ? null : other.getPortfolioUrl())
                .status(c.getStatus())
                .isRequester(isRequester)
                .build();
    }
}