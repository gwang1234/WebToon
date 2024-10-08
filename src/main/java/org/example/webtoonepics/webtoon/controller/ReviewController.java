package org.example.webtoonepics.webtoon.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.webtoonepics.community.dto.ProvideDto;
import org.example.webtoonepics.community.dto.base.DefaultRes;
import org.example.webtoonepics.community.entity.Community;
import org.example.webtoonepics.community.exception.ResponseMessage;
import org.example.webtoonepics.community.exception.StatusCode;
import org.example.webtoonepics.jwt_login.dto.CustomUserDetails;
import org.example.webtoonepics.webtoon.dto.ReviewRequest;
import org.example.webtoonepics.webtoon.dto.ReviewResponse;
import org.example.webtoonepics.webtoon.entity.Review;
import org.example.webtoonepics.webtoon.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "리뷰 조회 (20개 씩)", description = "id값과 pageSize를 입력하여 조회")
    @GetMapping("/reviews/{webtoonId}")
    public Page<ReviewResponse> findReviews(@PathVariable(name = "webtoonId") Long webtoonId,
            @RequestParam(name = "page", defaultValue = "0") int page) {

        Page<Review> reviewPage = reviewService.findByReviews(webtoonId, page);
        return reviewPage.map(ReviewResponse::new);
    }

    @Operation(summary = "리뷰 작성", description = "id값으로 리뷰 작성")
    @PostMapping("/reviews")
    public ResponseEntity<DefaultRes<String>> writeReview(@RequestBody ReviewRequest reviewRequest,
                                                          @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null && (reviewRequest.getProvider_id() == null || reviewRequest.getProvider_id().isEmpty())) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.UNAUTHORIZED, ResponseMessage.NO_AUTH), HttpStatus.UNAUTHORIZED);
        }
        if (userDetails != null && (reviewRequest.getProvider_id() != null && !reviewRequest.getProvider_id().isEmpty())) {
            SecurityContextHolder.clearContext();
            userDetails = null;
        }
        try {
            // 사용자 이메일 가져오기
            String email;
            if (userDetails != null) {
                email = userDetails.getUsername();
            } else {
                email = reviewService.findByProviderId(reviewRequest.getProvider_id());
            }

            Boolean target = reviewService.save(reviewRequest, email);
            if (!target) {
                return new ResponseEntity<>(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.NO_WRITE), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(DefaultRes.res(StatusCode.CREATED, ResponseMessage.SUCCESS), HttpStatus.CREATED);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(DefaultRes.res(StatusCode.INTERNAL_SERVER_ERROR, "에러 발생: " + errorMessage));
        }
    }

    @Operation(summary = "리뷰 수정", description = "id값으로 리뷰 수정")
    @PutMapping("/reviews/{id}")
    public ResponseEntity<DefaultRes<String>> updateReview(@PathVariable(name = "id") Long id, @AuthenticationPrincipal CustomUserDetails userDetails,
                                               @RequestBody ReviewRequest reviewRequest)
    {
        if (userDetails == null && (reviewRequest.getProvider_id() == null || reviewRequest.getProvider_id().isEmpty())) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.UNAUTHORIZED, ResponseMessage.NO_AUTH), HttpStatus.UNAUTHORIZED);
        }
        if (userDetails != null && (reviewRequest.getProvider_id() != null && !reviewRequest.getProvider_id().isEmpty())) {
            SecurityContextHolder.clearContext();
            userDetails = null;
        }
        try {
            // 사용자 이메일 가져오기
            String email;
            if (userDetails != null) {
                email = userDetails.getUsername();
            } else {
                email = reviewService.findByProviderId(reviewRequest.getProvider_id());
            }

            String email1 = reviewService.getEmail(id);
            if (email == null || !Objects.equals(email1, email)) {
                return new ResponseEntity<>(DefaultRes.res(StatusCode.FORBIDDEN, ResponseMessage.DIFF_USER), HttpStatus.FORBIDDEN);
            }

            Review review = reviewService.getReviewId(id);
            if (review == null) {
                return new ResponseEntity<>(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.NO_PAGE), HttpStatus.BAD_REQUEST);
            }

            Review review1 = reviewService.updateReview(review, reviewRequest);
            if (review1 == null) {
                return new ResponseEntity<>(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.WRONG_ID), HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.SERCH_WRONG + e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.INTERNAL_SERVER_ERROR, "서버 오류: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "리뷰 삭제", description = "id값으로 리뷰 삭제")
    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<DefaultRes<String>> deleteReview(@PathVariable(name = "id") Long id, @AuthenticationPrincipal CustomUserDetails userDetails,
                                             @RequestBody ProvideDto provideDto) {
        if (userDetails == null && (provideDto.getProvider_id() == null || provideDto.getProvider_id().isEmpty())) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.UNAUTHORIZED, ResponseMessage.NO_AUTH), HttpStatus.UNAUTHORIZED);
        }
        if (userDetails != null && (provideDto.getProvider_id() != null && !provideDto.getProvider_id().isEmpty())) {
            SecurityContextHolder.clearContext();
            userDetails = null;
        }
        try {
            // 사용자 이메일 가져오기
            String email;
            if (userDetails != null) {
                email = userDetails.getUsername();
            } else {
                email = reviewService.findByProviderId(provideDto.getProvider_id());
            }

            Review review = reviewService.getReviewId(id);
            if (review == null) {
                return new ResponseEntity<>(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.NO_PAGE), HttpStatus.BAD_REQUEST);
            }
            String email1 = reviewService.getEmail(id);
            if (email1 == null || !Objects.equals(email, email1)) {
                return new ResponseEntity<>(DefaultRes.res(StatusCode.FORBIDDEN, ResponseMessage.DIFF_USER), HttpStatus.FORBIDDEN);
            }
            reviewService.deleteReview(review);

            return new ResponseEntity<>(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.SERCH_WRONG + e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.INTERNAL_SERVER_ERROR, "서버 오류: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
