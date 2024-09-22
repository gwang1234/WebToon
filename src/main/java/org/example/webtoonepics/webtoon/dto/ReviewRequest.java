package org.example.webtoonepics.webtoon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.webtoonepics.webtoon.entity.Review;
import org.example.webtoonepics.webtoon.entity.Webtoon;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {

    private String writer;
    private String content;
    private short star;

    public Review toEntity(Webtoon webtoon) {
        return Review.builder()
                .webtoonInfo(webtoon)
                .star(star)
                .writer(writer)
                .content(content)
                .build();
    }
}
