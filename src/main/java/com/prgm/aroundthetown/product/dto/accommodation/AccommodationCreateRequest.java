package com.prgm.aroundthetown.product.dto.accommodation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationCreateRequest {
    private Long id;
    private String name;
}
