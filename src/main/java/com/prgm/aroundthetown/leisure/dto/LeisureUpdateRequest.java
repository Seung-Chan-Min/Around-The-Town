package com.prgm.aroundthetown.leisure.dto;

import com.prgm.aroundthetown.product.dto.LocationRequest;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class LeisureUpdateRequest {
    private Long id;
    private String leisureInformation;
    private String usecase;
    private String leisureNotice;
    private LocalDateTime expirationDate;
    private String category;
    private String refundRule;
    private String phoneNumber;
    private String businessRegistrationNumber;
    private String businessAddress;
    private String businessName;
    private String region;
    private LocationRequest location;
}
