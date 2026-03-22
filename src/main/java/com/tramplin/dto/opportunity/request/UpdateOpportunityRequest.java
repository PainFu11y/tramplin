package com.tramplin.dto.opportunity.request;

import com.tramplin.enums.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateOpportunityRequest {
    private String title;
    private String description;
    private OpportunityType type;
    private WorkFormat workFormat;
    private EmploymentType employmentType;
    private Level level;
    private Integer salary;
    private String city;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private LocalDate expirationDate;
    private LocalDate eventDate;
    private List<String> tags;
}