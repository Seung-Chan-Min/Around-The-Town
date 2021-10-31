package com.prgm.aroundthetown.review.dto;

import com.prgm.aroundthetown.review.entity.Review;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class ReviewImageDto {
    private Long id;
    private String IMAGE_PATH;
    private Review review;
}
