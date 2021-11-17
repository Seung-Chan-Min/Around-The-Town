package com.prgm.aroundthetown.review.service;

import com.prgm.aroundthetown.review.entity.ReviewImage;
import com.prgm.aroundthetown.review.repository.ReviewImageRepository;
import com.prgm.aroundthetown.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewImageServiceImpl implements ReviewImageService {
    private final ReviewImageRepository reviewImageRepository;
    private final ReviewRepository reviewRepository;

    @Override
    @Transactional
    public void createReviewImage(final Long reviewId, final List<String> reviewImages) {
        for (final String imagePath : reviewImages) {
            reviewImageRepository.save(ReviewImage.builder()
                    .IMAGE_PATH(imagePath)
                    .review(reviewRepository.getById(reviewId))
                    .build());
        }
    }

    @Override
    @Transactional
    public void deleteAllReviewImage(final Long reviewId) {
        reviewRepository.getById(reviewId)
                .getReviewImages()
                .forEach(reviewImage -> reviewImage.setIsDeleted(true));
    }
}
