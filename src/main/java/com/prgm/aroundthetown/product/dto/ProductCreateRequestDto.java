package com.prgm.aroundthetown.product.dto;

import com.prgm.aroundthetown.product.Region;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class ProductCreateRequestDto {
    private Long hostId;
    private String refundRule;
    private String phoneNumber;
    private String businessRegistrationNumber;
    private String businessAddress;
    private String businessName;
    private Region region;
    private LocationDto location;
}
