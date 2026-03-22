package com.tramplin.dto.response;

import com.tramplin.enums.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OpportunityResponse {
    private UUID id;
    private String title;
    private String description;
    private OpportunityType type;
    private WorkFormat workFormat;
    private EmploymentType employmentType;
    private Level level;
    private Integer salary;
    private String companyName;
    private Boolean companyVerified;
    private String city;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private LocalDate creationDate;
    private LocalDate expirationDate;
    private LocalDate eventDate;
    private OpportunityStatus status;
    private List<String> tags;
}