package com.prgm.aroundthetown.review.converter;

import com.prgm.aroundthetown.accommodation.dto.AccommodationDto;
import com.prgm.aroundthetown.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.member.dto.MemberResponseDto;
import com.prgm.aroundthetown.member.entity.Member;
import com.prgm.aroundthetown.review.dto.ReviewCreateRequestDto;
import com.prgm.aroundthetown.review.dto.ReviewDto;
import com.prgm.aroundthetown.review.dto.ReviewResponseDto;
import com.prgm.aroundthetown.review.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReviewConverter {
    public ReviewDto toReviewDto(final Review entity) {
        return ReviewDto.builder()
                .reviewId(entity.getReviewId())
                .content(entity.getContent())
                .score(entity.getScore())
                .accommodationName(entity.getAccommodation().getAccommodationName())
                .accommodationCategory(entity.getAccommodation().getAccommodationCategory())
                .build();
    }

    public ReviewResponseDto toResponseDto(final ReviewDto reviewDto,
                                           final MemberResponseDto memberDto,
                                           final AccommodationDto accommodationDto,
                                           final List<String> reviewImagePaths) {
        return ReviewResponseDto.builder()
                .memberResponseDto(memberDto)
                .accommodationDto(accommodationDto)
                .reviewId(reviewDto.getReviewId())
                .content(reviewDto.getContent())
                .score(reviewDto.getScore())
                .reviewImagePaths(reviewImagePaths)
                .build();
    }


    public Review toEntity(final ReviewCreateRequestDto dto,
                           final Member member,
                           final Accommodation accommodation) {
        return Review.builder()
                .content(dto.getContent())
                .score(dto.getScore())
                .member(member)
                .accommodation(accommodation)
                .build();
    }

}
