package com.prgm.aroundthetown.accommodation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class AccommodationCreateResponseDto {
    String businessName;
    String AccommodationName;
}
