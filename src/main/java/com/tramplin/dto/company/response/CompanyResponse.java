package com.tramplin.dto.company.response;

import lombok.*;
import java.util.List;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CompanyResponse {
    private UUID id;
    private String name;
    private String description;
    private Boolean isVerified;
    private List<String> socials;
    private List<String> spheres;
}