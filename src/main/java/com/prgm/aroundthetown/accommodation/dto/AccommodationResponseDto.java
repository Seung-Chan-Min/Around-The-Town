package com.prgm.aroundthetown.accommodation.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.prgm.aroundthetown.accommodation.entity.AccommodationCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class AccommodationResponseDto {
    private String accommodationName;
    private String accommodationNotice;
    private String optionNotice;
    private String guide;
    private AccommodationCategory accommodationCategory;
}
