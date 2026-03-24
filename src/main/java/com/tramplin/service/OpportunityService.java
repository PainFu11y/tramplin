package com.tramplin.service;

import com.tramplin.dto.opportunity.request.CreateOpportunityRequest;
import com.tramplin.dto.opportunity.request.UpdateOpportunityRequest;
import com.tramplin.dto.response.OpportunityResponse;
import com.tramplin.entity.Employer;
import com.tramplin.entity.Opportunity;
import com.tramplin.entity.Tag;
import com.tramplin.entity.User;
import com.tramplin.enums.OpportunityStatus;
import com.tramplin.enums.WorkFormat;
import com.tramplin.exception.ResourceNotFoundException;
import com.tramplin.repository.EmployerRepository;
import com.tramplin.repository.OpportunityRepository;
import com.tramplin.repository.TagRepository;
import com.tramplin.specification.OpportunitySpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OpportunityService {

    private final EmployerRepository employerRepository;
    private final OpportunityRepository opportunityRepository;
    private final TagRepository tagRepository;

    public Page<OpportunityResponse> getOpportunities(
            WorkFormat workFormat,
            List<String> tags,
            Integer salaryFrom,
            Integer salaryTo,
            Pageable pageable) {

        Specification<Opportunity> spec = Specification
                .where(OpportunitySpecification.isActive())
                .and(OpportunitySpecification.hasWorkFormat(workFormat))
                .and(OpportunitySpecification.hasTags(tags))
                .and(OpportunitySpecification.salaryFrom(salaryFrom))
                .and(OpportunitySpecification.salaryTo(salaryTo));

        return opportunityRepository.findAll(spec, pageable)
                .map(this::toResponse);
    }

    @Transactional
    public OpportunityResponse create(User currentUser, CreateOpportunityRequest request) {
        Employer employer = getVerifiedEmployer(currentUser);

        List<Tag> tags = resolveTags(request.getTags());

        Opportunity opportunity = Opportunity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .type(request.getType())
                .workFormat(request.getWorkFormat())
                .employmentType(request.getEmploymentType())
                .level(request.getLevel())
                .salary(request.getSalary())
                .company(employer.getCompany())
                .city(request.getCity())
                .address(request.getAddress())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .creationDate(LocalDate.now())
                .expirationDate(request.getExpirationDate())
                .eventDate(request.getEventDate())
                .createdBy(employer)
                .status(OpportunityStatus.MODERATION)
                .tags(tags)
                .build();

        return toResponse(opportunityRepository.save(opportunity));
    }

    @Transactional
    public OpportunityResponse update(User currentUser, UUID opportunityId, UpdateOpportunityRequest request) {
        Employer employer = getVerifiedEmployer(currentUser);
        Opportunity opportunity = getOwnedOpportunity(opportunityId, employer);

        if (request.getTitle() != null) opportunity.setTitle(request.getTitle());
        if (request.getDescription() != null) opportunity.setDescription(request.getDescription());
        if (request.getType() != null) opportunity.setType(request.getType());
        if (request.getWorkFormat() != null) opportunity.setWorkFormat(request.getWorkFormat());
        if (request.getEmploymentType() != null) opportunity.setEmploymentType(request.getEmploymentType());
        if (request.getLevel() != null) opportunity.setLevel(request.getLevel());
        if (request.getSalary() != null) opportunity.setSalary(request.getSalary());
        if (request.getCity() != null) opportunity.setCity(request.getCity());
        if (request.getAddress() != null) opportunity.setAddress(request.getAddress());
        if (request.getLatitude() != null) opportunity.setLatitude(request.getLatitude());
        if (request.getLongitude() != null) opportunity.setLongitude(request.getLongitude());
        if (request.getExpirationDate() != null) opportunity.setExpirationDate(request.getExpirationDate());
        if (request.getEventDate() != null) opportunity.setEventDate(request.getEventDate());
        if (request.getTags() != null) opportunity.setTags(resolveTags(request.getTags()));

        return toResponse(opportunityRepository.save(opportunity));
    }

    @Transactional
    public OpportunityResponse archive(User currentUser, UUID opportunityId) {
        Employer employer = getVerifiedEmployer(currentUser);
        Opportunity opportunity = getOwnedOpportunity(opportunityId, employer);

        if (opportunity.getStatus() == OpportunityStatus.ARCHIVED) {
            throw new IllegalStateException("Opportunity is already archived");
        }

        opportunity.setStatus(OpportunityStatus.ARCHIVED);
        return toResponse(opportunityRepository.save(opportunity));
    }

    private Employer getVerifiedEmployer(User currentUser) {
        Employer employer = employerRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Employer profile not found"));

        if (!Boolean.TRUE.equals(employer.getCompany().getIsVerified())) {
            throw new IllegalStateException("Your company is not verified yet.");
        }

        return employer;
    }

    private Opportunity getOwnedOpportunity(UUID opportunityId, Employer employer) {
        Opportunity opportunity = opportunityRepository.findById(opportunityId)
                .orElseThrow(() -> new ResourceNotFoundException("Opportunity not found"));

        if (!opportunity.getCreatedBy().getId().equals(employer.getId())) {
            throw new IllegalStateException("You don't have permission to modify this opportunity");
        }

        return opportunity;
    }

    private List<Tag> resolveTags(List<String> tagNames) {
        if (tagNames == null || tagNames.isEmpty()) return new ArrayList<>();
        return tagRepository.findByNameIn(tagNames);
    }

    public Page<OpportunityResponse> getCompanyOpportunities(
            UUID companyId,
            String search,
            Pageable pageable) {

        Specification<Opportunity> spec = Specification
                .where(OpportunitySpecification.belongsToCompany(companyId))
                .and(OpportunitySpecification.searchByKeyword(search));

        return opportunityRepository.findAll(spec, pageable).map(this::toResponse);
    }

    private OpportunityResponse toResponse(Opportunity o) {
        return OpportunityResponse.builder()
                .id(o.getId())
                .title(o.getTitle())
                .description(o.getDescription())
                .type(o.getType())
                .workFormat(o.getWorkFormat())
                .employmentType(o.getEmploymentType())
                .level(o.getLevel())
                .salary(o.getSalary())
                .companyName(o.getCompany().getName())
                .companyVerified(o.getCompany().getIsVerified())
                .city(o.getCity())
                .address(o.getAddress())
                .latitude(o.getLatitude())
                .longitude(o.getLongitude())
                .creationDate(o.getCreationDate())
                .expirationDate(o.getExpirationDate())
                .eventDate(o.getEventDate())
                .status(o.getStatus())
                .tags(o.getTags().stream().map(Tag::getName).collect(Collectors.toList()))
                .build();
    }
}