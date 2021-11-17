package com.prgm.aroundthetown.accommodation.dto;

import com.prgm.aroundthetown.accommodation.entity.AccommodationOptionCategory;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class AccommodationOptionDto {
    private AccommodationOptionCategory accommodationOptionCategory;
}
