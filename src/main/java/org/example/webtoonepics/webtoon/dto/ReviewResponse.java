package org.example.webtoonepics.webtoon.dto;

import lombok.Getter;
import org.example.webtoonepics.user.entity.User;
import org.example.webtoonepics.webtoon.entity.Review;

@Getter
public class ReviewResponse {

    private short star;
    private User userInfo;
    private String content;

    public ReviewResponse(Review review) {
        this.star = review.getStar();
        this.userInfo = review.getUserInfo();
        this.content = review.getContent();
    }
}
