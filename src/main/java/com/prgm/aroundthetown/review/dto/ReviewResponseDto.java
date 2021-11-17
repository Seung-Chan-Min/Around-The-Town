package com.prgm.aroundthetown.review.dto;

import com.prgm.aroundthetown.accommodation.dto.AccommodationDto;
import com.prgm.aroundthetown.member.dto.MemberResponseDto;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class ReviewResponseDto {
    private MemberResponseDto memberResponseDto;
    private AccommodationDto accommodationDto;
    private Long reviewId;
    private String content;
    private int score;
    private List<String> reviewImagePaths;
}
