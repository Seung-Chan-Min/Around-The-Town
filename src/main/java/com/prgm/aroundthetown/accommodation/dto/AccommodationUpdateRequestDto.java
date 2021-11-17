package com.prgm.aroundthetown.accommodation.dto;

import com.prgm.aroundthetown.accommodation.entity.AccommodationCategory;
import com.prgm.aroundthetown.product.dto.ProductCreateRequestDto;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class AccommodationUpdateRequestDto {
    private Long hostId;//로그인달면 제거

    private ProductCreateRequestDto productDto;
    private String accommodationName;
    private String accommodationNotice;
    private String optionNotice;
    private String guide;
    private AccommodationCategory accommodationCategory;
    private List<AccommodationOptionDto> accommodationOptions;
}
