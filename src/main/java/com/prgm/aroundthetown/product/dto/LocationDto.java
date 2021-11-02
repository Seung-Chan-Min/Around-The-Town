package com.prgm.aroundthetown.product.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class LocationDto {
    private String howToVisit;
    private Double latitude;
    private Double longitude;
    private String content;
}
