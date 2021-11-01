package com.prgm.aroundthetown.review.service;

import com.prgm.aroundthetown.common.exception.NotFoundException;
import com.prgm.aroundthetown.review.converter.ReviewConverter;
import com.prgm.aroundthetown.review.dto.ReviewCreateDto;
import com.prgm.aroundthetown.review.dto.ReviewDto;
import com.prgm.aroundthetown.review.dto.ReviewImageCreateDto;
import com.prgm.aroundthetown.review.dto.ReviewUpdateDto;
import com.prgm.aroundthetown.review.entity.Review;
import com.prgm.aroundthetown.review.repository.ReviewImageRepository;
import com.prgm.aroundthetown.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository repository;
    private final ReviewImageRepository imageRepository;
    private final ReviewConverter converter;

    @Transactional
    public Long createReview(final ReviewCreateDto dto) {
        return repository.save(converter.toEntity(dto)).getReviewId();
    }

    @Transactional
    public Long createReviewImage(final ReviewImageCreateDto dto) {
        return imageRepository.save(converter.toEntity(dto)).getId();
    }

    public ReviewDto findById(final Long reviewId) {
        return repository.findById(reviewId)
                .map(converter::toDto)
                .orElseThrow(() -> new NotFoundException("Review is not found"));
    }

    @Transactional
    public Long updateReview(final ReviewUpdateDto dto) {
        final Review entity = repository.findById(dto.getReviewId()).get();
        entity.update(dto.getContent(), dto.getScore(), dto.getReviewImages());
        return repository.save(entity).getReviewId();
    }

    @Transactional
    public void deleteReview(final Long reviewId) {
        final Review entity = repository.findById(reviewId).get();
        entity.setIsDeleted(true);
        repository.save(entity);
    }
}
