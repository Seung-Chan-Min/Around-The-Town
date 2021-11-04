package com.prgm.aroundthetown.review.controller;

import com.prgm.aroundthetown.review.dto.*;
import com.prgm.aroundthetown.review.service.ReviewImageServiceImpl;
import com.prgm.aroundthetown.review.service.ReviewServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewServiceImpl reviewService;
    private final ReviewImageServiceImpl reviewImageService;

    @PostMapping("/review")
    public ResponseEntity<Long> createReview(
            @RequestBody final ReviewCreateRequestDto req
    ) {
        final Long reviewId = reviewService.createReview(req);
        reviewImageService.createReviewImage(req.getReviewImagePaths(), reviewId);
        return new ResponseEntity<>(reviewId, HttpStatus.CREATED);
    }

    @GetMapping("/review/{memberId}")
    public ResponseEntity<ReviewFindAllByMemberResponseDto> findAllByMember(
            @PathVariable final Long memberId
    ) {
        final ReviewFindAllByMemberResponseDto res = reviewService.findAllReviewsByMember(memberId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/review/{reviewId}")
    public ResponseEntity<ReviewFindByIdResponseDto> findById(
            @PathVariable final Long reviewId
    ) {
        final ReviewFindByIdResponseDto res = reviewService.findById(reviewId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/review/{reviewId}")
    public ResponseEntity<ReviewDto> updateReview(
            @PathVariable final Long reviewId,
            @RequestBody final ReviewUpdateRequestDto req
    ) {
        reviewImageService.deleteAllReviewImage(reviewId);
        reviewImageService.createReviewImage(req.getReviewImagePaths(), reviewId);
        final ReviewDto res = reviewService.updateReview(reviewId, req);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @DeleteMapping("/review/{reviewId}")
    public ResponseEntity<ReviewDto> deleteReview(
            @PathVariable final Long reviewId
    ) {
        final ReviewDto res = reviewService.deleteReview(reviewId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
