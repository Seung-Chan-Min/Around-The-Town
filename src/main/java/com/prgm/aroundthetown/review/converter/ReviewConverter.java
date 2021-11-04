package com.prgm.aroundthetown.review.converter;

import com.prgm.aroundthetown.accommodation.converter.AccommodationConverter;
import com.prgm.aroundthetown.accommodation.repository.AccommodationRepository;
import com.prgm.aroundthetown.member.converter.MemberConverter;
import com.prgm.aroundthetown.member.repository.MemberRepository;
import com.prgm.aroundthetown.review.dto.ReviewCreateRequestDto;
import com.prgm.aroundthetown.review.dto.ReviewDto;
import com.prgm.aroundthetown.review.dto.ReviewFindByIdResponseDto;
import com.prgm.aroundthetown.review.entity.Review;
import com.prgm.aroundthetown.review.entity.ReviewImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReviewConverter {
    private final MemberRepository memberRepository;
    private final AccommodationRepository accommodationRepository;

    private final MemberConverter memberConverter;
    private final AccommodationConverter accommodationConverter;

    public ReviewDto toReviewDto(final Review entity) {
        return ReviewDto.builder()
                .reviewId(entity.getReviewId())
                .content(entity.getContent())
                .score(entity.getScore())
                .accommodationName(entity.getAccommodation().getAccommodationName())
                .accommodationCategory(entity.getAccommodation().getAccommodationCategory())
                .build();
    }

    public ReviewFindByIdResponseDto toFindByIdResponseDto(final Review entity) {
        return ReviewFindByIdResponseDto.builder()
                .memberDto(
                        memberConverter.toDto(
                                entity.getMember()
                        ))
                .accommodationDto(
                        accommodationConverter.toDto(
                                entity.getAccommodation()
                        ))
                .reviewId(entity.getReviewId())
                .content(entity.getContent())
                .score(entity.getScore())
                .reviewImagePaths(
                        entity.getReviewImages()
                                .stream()
                                .map(ReviewImage::getIMAGE_PATH)
                                .collect(Collectors.toList()))
                .build();
    }


    public Review toEntity(final ReviewCreateRequestDto dto) {
        return Review.builder()
                .content(dto.getContent())
                .score(dto.getScore())
                .member(memberRepository.getById(dto.getMemberId()))
                .accommodation(accommodationRepository.getById(dto.getAccommodationId()))
                .build();
    }

}
