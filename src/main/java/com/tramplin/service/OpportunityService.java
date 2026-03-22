package com.tramplin.service;

import com.tramplin.dto.response.OpportunityResponse;
import com.tramplin.entity.Opportunity;
import com.tramplin.entity.Tag;
import com.tramplin.enums.WorkFormat;
import com.tramplin.repository.OpportunityRepository;
import com.tramplin.specification.OpportunitySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OpportunityService {

    private final OpportunityRepository opportunityRepository;

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