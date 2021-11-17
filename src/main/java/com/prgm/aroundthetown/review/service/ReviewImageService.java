package com.prgm.aroundthetown.review.service;

import java.util.List;

public interface ReviewImageService {
    void createReviewImage(final Long reviewId, final List<String> reviewImages);

    void deleteAllReviewImage(final Long reviewId);
}
