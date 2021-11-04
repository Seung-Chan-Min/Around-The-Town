package com.prgm.aroundthetown.review.service;

import com.prgm.aroundthetown.member.repository.MemberRepository;
import com.prgm.aroundthetown.review.converter.ReviewConverter;
import com.prgm.aroundthetown.review.dto.*;
import com.prgm.aroundthetown.review.entity.Review;
import com.prgm.aroundthetown.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;

    private final ReviewConverter converter;

    @Transactional
    public Long createReview(final ReviewCreateRequestDto dto) {
        return reviewRepository.save(converter.toEntity(dto)).getReviewId();
    }

    public ReviewFindAllByMemberResponseDto findAllReviewsByMember(final Long memberId) {
        return ReviewFindAllByMemberResponseDto.builder()
                .memberId(memberId)
                .reviewDtos(memberRepository.getById(memberId)
                        .getReviews()
                        .stream()
                        .map(converter::toReviewDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public ReviewFindByIdResponseDto findById(final Long reviewId) {
        return converter.toFindByIdResponseDto(reviewRepository.getById(reviewId));
    }

    @Transactional
    public ReviewDto updateReview(final Long reviewId, final ReviewUpdateRequestDto dto) {
        final Review entity = reviewRepository.getById(reviewId);
        entity.update(dto.getContent(), dto.getScore());
        return converter.toReviewDto(reviewRepository.save(entity));
    }

    @Transactional
    public ReviewDto deleteReview(final Long reviewId) {
        final Review entity = reviewRepository.getById(reviewId);
        entity.setIsDeleted(true);
        return converter.toReviewDto(reviewRepository.save(entity));
    }
}
