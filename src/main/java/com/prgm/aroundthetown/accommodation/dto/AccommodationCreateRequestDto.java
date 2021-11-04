package com.prgm.aroundthetown.accommodation.dto;

import com.prgm.aroundthetown.accommodation.entity.AccommodationCategory;
import com.prgm.aroundthetown.product.dto.ProductDto;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class AccommodationCreateRequestDto {
    private Long hostId;
    private ProductDto productDto;
    private String accommodationName;
    private String accommodationNotice;
    private String optionNotice;
    private String guide;
    private AccommodationCategory accommodationCategory;
}
