package com.tramplin.dto.favourite.response;

import lombok.*;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FavouriteResponse {
    private UUID opportunityId;
    private boolean isFavourite;
}