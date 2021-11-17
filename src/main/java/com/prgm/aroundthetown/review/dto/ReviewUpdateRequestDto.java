package com.prgm.aroundthetown.review.dto;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class ReviewUpdateRequestDto {
    private String content;
    private int score;
    private List<String> reviewImagePaths;
}
