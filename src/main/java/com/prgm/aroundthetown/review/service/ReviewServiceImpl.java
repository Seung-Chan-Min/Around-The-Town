package com.prgm.aroundthetown.review.service;

import com.prgm.aroundthetown.accommodation.converter.AccommodationConverter;
import com.prgm.aroundthetown.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.accommodation.repository.AccommodationRepository;
import com.prgm.aroundthetown.member.converter.MemberConverter;
import com.prgm.aroundthetown.member.entity.Member;
import com.prgm.aroundthetown.member.repository.MemberRepository;
import com.prgm.aroundthetown.review.converter.ReviewConverter;
import com.prgm.aroundthetown.review.dto.ReviewCreateRequestDto;
import com.prgm.aroundthetown.review.dto.ReviewDto;
import com.prgm.aroundthetown.review.dto.ReviewResponseDto;
import com.prgm.aroundthetown.review.dto.ReviewUpdateRequestDto;
import com.prgm.aroundthetown.review.entity.Review;
import com.prgm.aroundthetown.review.entity.ReviewImage;
import com.prgm.aroundthetown.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final AccommodationRepository accommodationRepository;

    private final ReviewConverter reviewConverter;
    private final MemberConverter memberConverter;
    private final AccommodationConverter accommodationConverter;

    @Override
    @Transactional
    public Long createReview(final ReviewCreateRequestDto dto) {
        final Member member = memberRepository.getById(dto.getMemberId());
        final Accommodation accommodation = accommodationRepository.getById(dto.getAccommodationId());
        return reviewRepository
                .save(reviewConverter.toEntity(dto, member, accommodation))
                .getReviewId();
    }

    @Override
    public List<ReviewDto> findAllReviewsByMember(final Long memberId) {
        return memberRepository.getById(memberId)
                .getReviews()
                .stream()
                .map(reviewConverter::toReviewDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewResponseDto findById(final Long reviewId) {
        final Review entity = reviewRepository.getById(reviewId);
        return getReviewResponseDto(entity);
    }

    @Override
    @Transactional
    public ReviewResponseDto updateReview(final Long reviewId, final ReviewUpdateRequestDto dto) {
        final Review entity = reviewRepository.getById(reviewId);
        entity.update(dto.getContent(), dto.getScore());
        reviewRepository.save(entity);
        return getReviewResponseDto(entity);
    }

    @Override
    @Transactional
    public ReviewResponseDto deleteReview(final Long reviewId) {
        final Review entity = reviewRepository.getById(reviewId);
        entity.setIsDeleted(true);
        reviewRepository.save(entity);
        return getReviewResponseDto(entity);
    }

    private ReviewResponseDto getReviewResponseDto(final Review entity) {
        return reviewConverter.toResponseDto(
                reviewConverter.toReviewDto(entity),
                memberConverter.toDto(entity.getMember()),
                accommodationConverter.toDto(entity.getAccommodation()),
                entity.getReviewImages()
                        .stream()
                        .map(ReviewImage::getIMAGE_PATH)
                        .collect(Collectors.toList()));
    }
}
