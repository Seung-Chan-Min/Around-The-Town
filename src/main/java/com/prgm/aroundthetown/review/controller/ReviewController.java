package com.prgm.aroundthetown.review.controller;

import com.prgm.aroundthetown.review.dto.ReviewCreateRequestDto;
import com.prgm.aroundthetown.review.dto.ReviewDto;
import com.prgm.aroundthetown.review.dto.ReviewResponseDto;
import com.prgm.aroundthetown.review.dto.ReviewUpdateRequestDto;
import com.prgm.aroundthetown.review.service.ReviewImageServiceImpl;
import com.prgm.aroundthetown.review.service.ReviewServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewServiceImpl reviewService;
    private final ReviewImageServiceImpl reviewImageService;

    @PostMapping("/review")
    public ResponseEntity<Long> createReview(
            @RequestBody final ReviewCreateRequestDto req
    ) {
        final Long reviewId = reviewService.createReview(req);
        reviewImageService.createReviewImage(reviewId, req.getReviewImagePaths());
        return new ResponseEntity<>(reviewId, HttpStatus.CREATED);
    }

    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewDto>> findAllByMember(
            @RequestParam final Long memberId
    ) {
        final List<ReviewDto> res = reviewService.findAllReviewsByMember(memberId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewResponseDto> findById(
            @PathVariable final Long reviewId
    ) {
        final ReviewResponseDto res = reviewService.findById(reviewId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PatchMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewResponseDto> updateReview(
            @PathVariable final Long reviewId,
            @RequestBody final ReviewUpdateRequestDto req
    ) {
        reviewImageService.deleteAllReviewImage(reviewId);
        reviewImageService.createReviewImage(reviewId, req.getReviewImagePaths());
        final ReviewResponseDto res = reviewService.updateReview(reviewId, req);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewResponseDto> deleteReview(
            @PathVariable final Long reviewId
    ) {
        final ReviewResponseDto res = reviewService.deleteReview(reviewId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
