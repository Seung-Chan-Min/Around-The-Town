package com.prgm.aroundthetown.product.dto;

import com.prgm.aroundthetown.accommodation.dto.AccommodationDto;
import com.prgm.aroundthetown.leisure.dto.LeisureDto;
import com.prgm.aroundthetown.product.Region;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class ProductDto {
    private Long productId;
    private AccommodationDto accommodationDto;
    private LeisureDto leisureDto;
    private Region region;
    private String refundRule;
    private LocationDto locationDto;
    private String phoneNumber;
    private String businessRegistrationNumber;
    private String businessAddress;
    private String businessName;
}
