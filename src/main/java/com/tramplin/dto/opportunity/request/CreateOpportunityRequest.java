package com.tramplin.dto.opportunity.request;

import com.tramplin.enums.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOpportunityRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Type is required")
    private OpportunityType type;

    @NotNull(message = "Work format is required")
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