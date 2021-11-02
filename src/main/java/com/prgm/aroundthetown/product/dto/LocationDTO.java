package com.prgm.aroundthetown.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LocationDTO {
    private String howToVisit;
    private Double latitude;
    private Double longitude;
    private String content;
}
