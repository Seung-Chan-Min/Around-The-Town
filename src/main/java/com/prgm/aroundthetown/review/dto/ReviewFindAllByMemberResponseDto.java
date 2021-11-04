package com.prgm.aroundthetown.review.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class ReviewFindAllByMemberResponseDto {
    Long memberId;
    List<ReviewDto> reviewDtos = new ArrayList<>();
}
