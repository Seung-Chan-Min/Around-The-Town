package com.prgm.aroundthetown.accommodation.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class AccommodationDeleteDto {
    private String accommodationName;
}
