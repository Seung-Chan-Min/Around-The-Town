package com.prgm.aroundthetown.review.controller;

import com.prgm.aroundthetown.common.response.ApiResponse;
import com.prgm.aroundthetown.review.dto.ReviewCreateRequestDto;
import com.prgm.aroundthetown.review.dto.ReviewDto;
import com.prgm.aroundthetown.review.dto.ReviewResponseDto;
import com.prgm.aroundthetown.review.dto.ReviewUpdateRequestDto;
import com.prgm.aroundthetown.review.service.ReviewImageServiceImpl;
import com.prgm.aroundthetown.review.service.ReviewServiceImpl;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<ApiResponse<Long>> createReview(
            @RequestBody final ReviewCreateRequestDto req
    ) {
        final Long reviewId = reviewService.createReview(req);
        reviewImageService.createReviewImage(reviewId, req.getReviewImagePaths());
        return ResponseEntity.ok(ApiResponse.created(reviewId));
    }

    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewResponseDto>> findById(
            @PathVariable final Long reviewId
    ) {
        return ResponseEntity.ok(ApiResponse.ok(reviewService.findById(reviewId)));
    }

    @GetMapping("/reviews")
    public ResponseEntity<ApiResponse<List<ReviewDto>>> findAllByMember(
            @RequestParam final Long memberId
    ) {
        return ResponseEntity.ok(ApiResponse.ok(reviewService.findAllReviewsByMember(memberId)));
    }

    @PatchMapping("/reviews/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewResponseDto>> updateReview(
            @PathVariable final Long reviewId,
            @RequestBody final ReviewUpdateRequestDto req
    ) {
        reviewImageService.deleteAllReviewImage(reviewId);
        reviewImageService.createReviewImage(reviewId, req.getReviewImagePaths());
        return ResponseEntity.ok(ApiResponse.ok(reviewService.updateReview(reviewId, req)));
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewResponseDto>> deleteReview(
            @PathVariable final Long reviewId
    ) {
        return ResponseEntity.ok(ApiResponse.ok(reviewService.deleteReview(reviewId)));
    }
}
