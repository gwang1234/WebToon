package org.example.webtoonepics.webtoon.dto;

import lombok.Getter;
import org.example.webtoonepics.webtoon.entity.Review;

@Getter
public class ReviewResponse {

    private Long id;
    private short star;
    private Long userId;
    private String content;

    public ReviewResponse(Review review) {
        this.id = review.getId();
        this.star = review.getStar();
        this.userId = review.getUserInfo().getId();
        this.content = review.getContent();
    }
}
