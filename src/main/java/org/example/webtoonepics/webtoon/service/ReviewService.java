package org.example.webtoonepics.webtoon.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.webtoonepics.webtoon.dto.ReviewRequest;
import org.example.webtoonepics.webtoon.entity.Review;
import org.example.webtoonepics.webtoon.entity.Webtoon;
import org.example.webtoonepics.webtoon.repository.ReviewRepository;
import org.example.webtoonepics.webtoon.repository.WebtoonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {

    private final WebtoonRepository webtoonRepository;
    private final ReviewRepository reviewRepository;

    public Review save(Long id, ReviewRequest reviewRequest) {
        Webtoon webtoon = webtoonRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Webtoon not found" + id));

        return reviewRepository.save(reviewRequest.toEntity(webtoon));
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
