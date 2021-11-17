package com.prgm.aroundthetown.review.dto;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class ReviewCreateRequestDto {
    private String content;
    private int score;
    private Long memberId;
    private Long accommodationId;
    private List<String> reviewImagePaths;
}
