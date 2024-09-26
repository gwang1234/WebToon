package org.example.webtoonepics.webtoon.dto;

import lombok.Getter;
import org.example.webtoonepics.webtoon.entity.Review;

@Getter
public class ReviewResponse {

    private short star;
    private String writer;
    private String content;

    public ReviewResponse(Review review) {
        this.star = review.getStar();
        this.writer = review.getWriter();
        this.content = review.getContent();
    }
}
