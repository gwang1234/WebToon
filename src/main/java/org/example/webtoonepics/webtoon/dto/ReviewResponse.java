package org.example.webtoonepics.webtoon.dto;

import lombok.Getter;
import org.example.webtoonepics.webtoon.entity.Review;

@Getter
public class ReviewResponse {

    private Long id;
    private short star;
    private String userName;
    private String content;
    private Long webtoon_id;
    private String webtoon_title;

    public ReviewResponse(Review review) {
        this.id = review.getId();
        this.star = review.getStar();
        this.userName = review.getUserInfo().getUserName();
        this.content = review.getContent();
        this.webtoon_id = review.getWebtoonInfo().getId();
        this.webtoon_title = review.getWebtoonInfo().getTitle();
    }

}
