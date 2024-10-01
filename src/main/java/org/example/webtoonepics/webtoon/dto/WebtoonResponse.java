package org.example.webtoonepics.webtoon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.webtoonepics.webtoon.entity.Webtoon;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WebtoonResponse {

    private String title;
    private String provider;
    private int views;
    private String author;
    private String imgUrl;
    private String description;

    public WebtoonResponse(Webtoon webtoon) {
        this.title = webtoon.getTitle();
        this.provider = webtoon.getProvider();
        this.views = webtoon.getViews();
        this.author = webtoon.getAuthor();
        this.imgUrl = webtoon.getImgUrl();
        this.description = webtoon.getDescription();
    }
}
