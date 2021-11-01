package com.prgm.aroundthetown.product.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class LocationResponse {
    private String howToVisit;
    private Double latitude;
    private Double longitude;
    private String content;
}
