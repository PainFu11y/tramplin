package com.tramplin.dto.connection;

import com.tramplin.enums.ConnectionStatus;
import lombok.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConnectionResponse {
    private UUID id;
    private UUID seekerId;
    private String firstName;
    private String lastName;
    private String university;
    private String course;
    private String portfolioUrl;
    private ConnectionStatus status;
    private boolean isRequester;
}