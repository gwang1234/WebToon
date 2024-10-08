package org.example.webtoonepics.webtoon.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.webtoonepics.jwt_login.dto.CustomUserDetails;
import org.example.webtoonepics.user.entity.User;
import org.example.webtoonepics.user.repository.UserRepository;
import org.example.webtoonepics.user.service.CustomOAuth2User;
import org.example.webtoonepics.webtoon.dto.ReviewRequest;
import org.example.webtoonepics.webtoon.entity.Review;
import org.example.webtoonepics.webtoon.entity.Webtoon;
import org.example.webtoonepics.webtoon.repository.ReviewRepository;
import org.example.webtoonepics.webtoon.repository.WebtoonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {

    private final WebtoonRepository webtoonRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    // 리뷰 저장
    public Review save(ReviewRequest reviewRequest) {
        // 웹툰을 ID로 조회
        Webtoon webtoon = webtoonRepository.findById(reviewRequest.getWebtoonId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Webtoon ID"));

        // 현재 로그인된 유저 정보 가져오기
        User user = getCurrentUser();

//        User user = userRepository.findById(reviewRequest.getUserId()).orElseThrow(() -> new IllegalArgumentException("Invalid User ID"));
        return reviewRepository.save(reviewRequest.toEntity(webtoon, user));
    }

    // 리뷰 수정
    @Transactional
    public Review update(Long id, ReviewRequest reviewRequest) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Review not found" + id));

        // 현재 로그인된 유저 정보 가져오기
        User user = getCurrentUser();

        // 본인 작성 리뷰만 수정 가능
        if (!review.getUserInfo().getId().equals(user.getId())) {
            throw new IllegalStateException("본인 작성 리뷰만 수정 가능");
        }

        review.update(reviewRequest.getStar(), reviewRequest.getContent());
        return review;
    }

    // 특정 웹툰의 모든 리뷰 조회 (Read)
    public List<Review> findByReviews(Long id) {
        Webtoon webtoon = webtoonRepository.findById(id).orElseThrow(() -> new IllegalStateException("Webtoon not found" + id));
        return reviewRepository.findByWebtoonInfo(webtoon).orElseThrow(() -> new IllegalStateException("Webtoon not found" + id));
    }

    // 특정 웹툰의 리뷰 20개씩 조회(desc)
    public Page<Review> findByReviews(Long id, int page) {
        Webtoon webtoon = webtoonRepository.findById(id).orElseThrow(() -> new IllegalStateException("Webtoon not found" + id));
        Pageable pageable = PageRequest.of(page, 20, Sort.by(Sort.Direction.DESC,"id"));
        return reviewRepository.findByWebtoonInfo(webtoon, pageable);
    }

    // 리뷰 삭제 (Delete)
    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }

    // 현재 로그인된 유저 정보 반환 (JWT or OAuth2)
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // JWT 유저 처리
        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            return userRepository.findById(userDetails.getId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
        }

        // OAuth2 유저 처리
        else if (authentication.getPrincipal() instanceof OAuth2User) {
            CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
            return userRepository.findByProviderId(oauthUser.getName())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
        }

        throw new IllegalStateException("User is not authenticated");
    }
}
