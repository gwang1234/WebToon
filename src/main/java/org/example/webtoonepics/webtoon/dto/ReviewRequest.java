package org.example.webtoonepics.webtoon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.webtoonepics.user.entity.User;
import org.example.webtoonepics.webtoon.entity.Review;
import org.example.webtoonepics.webtoon.entity.Webtoon;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {

    private Long webtoonId;
    private Long userId;
    private String content;
    private short star;

    public Review toEntity(Webtoon webtoon, User user) {
        return Review.builder()
                .webtoonInfo(webtoon)
                .star(star)
                .userInfo(user)
                .content(content)
                .build();
    }
}
