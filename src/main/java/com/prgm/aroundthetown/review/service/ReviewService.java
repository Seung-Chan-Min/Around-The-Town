package com.prgm.aroundthetown.review.service;

import com.prgm.aroundthetown.review.dto.ReviewCreateRequestDto;
import com.prgm.aroundthetown.review.dto.ReviewDto;
import com.prgm.aroundthetown.review.dto.ReviewResponseDto;
import com.prgm.aroundthetown.review.dto.ReviewUpdateRequestDto;

import java.util.List;

public interface ReviewService {
    Long createReview(final ReviewCreateRequestDto dto);

    List<ReviewDto> findAllReviewsByMember(final Long memberId);

    ReviewResponseDto findById(final Long reviewId);

    ReviewResponseDto updateReview(final Long reviewId, final ReviewUpdateRequestDto dto);

    ReviewResponseDto deleteReview(final Long reviewId);
}
