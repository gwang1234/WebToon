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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {

    private final WebtoonRepository webtoonRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public Review save(ReviewRequest reviewRequest) {
        // 웹툰을 ID로 조회
        Webtoon webtoon = webtoonRepository.findById(reviewRequest.getWebtoonId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Webtoon ID"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
 
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
        String providerId = oauthUser.getProviderId();  // OAuth 제공자 ID
        String email = oauthUser.getEmail();            // 이메일

        User user = userRepository.findById(reviewRequest.getUserId()).orElseThrow(() -> new IllegalArgumentException("Invalid User ID"));
        return reviewRepository.save(reviewRequest.toEntity(webtoon, user));
    }

    @Transactional
    public Review update(Long id, ReviewRequest reviewRequest) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Review not found" + id));

        review.update(reviewRequest.getStar(), reviewRequest.getContent());
        return review;
    }

    public List<Review> findByReviews(Long id) {
        Webtoon webtoon = webtoonRepository.findById(id).orElseThrow(() -> new IllegalStateException("Webtoon not found" + id));
        return reviewRepository.findByWebtoonInfo(webtoon).orElseThrow(() -> new IllegalStateException("Webtoon not found" + id));
    }

    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }
}
