package com.tramplin.dto.company.request;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCompanyRequest {
    private String description;
    private String websiteUrl;
    private List<String> spheres;
}