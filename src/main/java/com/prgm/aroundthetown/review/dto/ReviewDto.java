package com.prgm.aroundthetown.review.dto;

import com.prgm.aroundthetown.accommodation.entity.AccommodationCategory;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class ReviewDto {
    private Long reviewId;
    private String content;
    private int score;
    private String accommodationName;
    private AccommodationCategory accommodationCategory;
}
