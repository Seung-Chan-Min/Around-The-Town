package com.prgm.aroundthetown.leisure.dto;

import com.prgm.aroundthetown.leisure.entity.LeisureCategory;
import com.prgm.aroundthetown.product.Region;
import com.prgm.aroundthetown.product.dto.LocationDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class LeisureDeleteResponseDto {
    private Long id;
    private String leisureInformation;
    private String usecase;
    private String leisureNotice;
    private LocalDateTime expirationDate;
    private LeisureCategory category;
    private String refundRule;
    private String phoneNumber;
    private String businessRegistrationNumber;
    private String businessAddress;
    private String businessName;
    private Region region;
    private LocationDto location;
}
