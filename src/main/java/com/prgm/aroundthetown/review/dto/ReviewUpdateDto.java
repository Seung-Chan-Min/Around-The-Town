package com.prgm.aroundthetown.review.dto;

import com.prgm.aroundthetown.review.entity.ReviewImage;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class ReviewUpdateDto {
    private Long reviewId;
    private String content;
    private int score;
    private List<ReviewImage> reviewImages;
}
