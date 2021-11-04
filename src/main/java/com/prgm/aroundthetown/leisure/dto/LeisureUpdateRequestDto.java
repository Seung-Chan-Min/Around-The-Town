package com.prgm.aroundthetown.leisure.dto;

import com.prgm.aroundthetown.leisure.entity.LeisureCategory;
import com.prgm.aroundthetown.product.Region;
import com.prgm.aroundthetown.product.dto.LocationDto;
import com.prgm.aroundthetown.product.dto.ProductUpdateRequestDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class LeisureUpdateRequestDto {
    private Long id;
    private String leisureInformation;
    private String usecase;
    private String leisureNotice;
    private LocalDateTime expirationDate;
    private LeisureCategory category;
    private ProductUpdateRequestDto productUpdateRequestDto;
}
