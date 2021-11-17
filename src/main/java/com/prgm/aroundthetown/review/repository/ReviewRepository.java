package com.prgm.aroundthetown.review.repository;

import com.prgm.aroundthetown.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
