package com.prgm.aroundthetown.accommodation.dto;

import com.prgm.aroundthetown.accommodation.entity.AccommodationCategory;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class AccommodationDto {
    private String accommodationName;
    private AccommodationCategory accommodationCategory;
    private String accommodationNotice;
    private String optionNotice;
    private String guide;
}
