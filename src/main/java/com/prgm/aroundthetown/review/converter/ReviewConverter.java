package com.prgm.aroundthetown.review.converter;

import com.prgm.aroundthetown.review.dto.ReviewCreateDto;
import com.prgm.aroundthetown.review.dto.ReviewDto;
import com.prgm.aroundthetown.review.dto.ReviewImageCreateDto;
import com.prgm.aroundthetown.review.dto.ReviewImageDto;
import com.prgm.aroundthetown.review.entity.Review;
import com.prgm.aroundthetown.review.entity.ReviewImage;
import org.springframework.stereotype.Component;

@Component
public class ReviewConverter {
    public ReviewDto toDto(final Review entity) {
        return ReviewDto.builder()
                .reviewId(entity.getReviewId())
                .content(entity.getContent())
                .score(entity.getScore())
                .member(entity.getMember())
                .accommodation(entity.getAccommodation())
                .reviewImages(entity.getReviewImages())
                .build();
    }

    public Review toEntity(final ReviewCreateDto dto) {
        return Review.builder()
                .content(dto.getContent())
                .score(dto.getScore())
                .member(dto.getMember())
                .accommodation(dto.getAccommodation())
                .build();
    }

    public ReviewImageDto toDto(final ReviewImage entity) {
        return ReviewImageDto.builder()
                .id(entity.getId())
                .IMAGE_PATH(entity.getIMAGE_PATH())
                .review(entity.getReview())
                .build();
    }

    public ReviewImage toEntity(final ReviewImageCreateDto dto) {
        return ReviewImage.builder()
                .IMAGE_PATH(dto.getIMAGE_PATH())
                .review(dto.getReview())
                .build();
    }
}
