package com.prgm.aroundthetown.review.dto;

import com.prgm.aroundthetown.member.entity.Member;
import com.prgm.aroundthetown.product.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.review.entity.ReviewImage;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class ReviewDto {
    private Long reviewId;
    private String content;
    private int score;
    private Member member;
    private Accommodation accommodation;
    private List<ReviewImage> reviewImages;
}
