package com.prgm.aroundthetown.review.dto;

import com.prgm.aroundthetown.member.entity.Member;
import com.prgm.aroundthetown.product.accommodation.entity.Accommodation;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class ReviewCreateDto {
    private String content;
    private int score;
    private Member member;
    private Accommodation accommodation;
}
