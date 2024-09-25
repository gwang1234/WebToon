package org.example.webtoonepics.webtoon.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.webtoonepics.webtoon.dto.ReviewRequest;
import org.example.webtoonepics.webtoon.dto.ReviewResponse;
import org.example.webtoonepics.webtoon.entity.Review;
import org.example.webtoonepics.webtoon.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "리뷰 조회", description = "id값으로 리뷰 조회")
    @GetMapping("/reviews/{id}")
    public ResponseEntity<List<ReviewResponse>> findReviews(@PathVariable(name = "id") Long id) {
        List<ReviewResponse> reviewResponses = reviewService.findByReviews(id).stream().map(ReviewResponse::new).toList();
        return ResponseEntity.ok().body(reviewResponses);
    }

    @Operation(summary = "리뷰 작성", description = "id값으로 리뷰 작성")
    @PostMapping("/reviews")
    public ResponseEntity<Review> writeReview(@RequestBody ReviewRequest reviewRequest) {
        Review writeReview = reviewService.save(reviewRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(writeReview);
    }

    @Operation(summary = "리뷰 수정", description = "id값으로 리뷰 수정")
    @PutMapping("/reviews/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable(name = "id") Long id, @RequestBody ReviewRequest reviewRequest) {
        Review updateReview = reviewService.update(id, reviewRequest);
        return ResponseEntity.ok().body(updateReview);
    }

    @Operation(summary = "리뷰 삭제", description = "id값으로 리뷰 삭제")
    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable(name = "id") Long id) {
        reviewService.delete(id);
        return ResponseEntity.ok().build();
    }
}
