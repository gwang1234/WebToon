package org.example.webtoonepics.community.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class CommunityDetailDto {

    private Long id;
    private String title;
    private String content;
    private int view;
    private int likes;
    private String createdAt;
    private String updatedAt;
    private String userName;

    @QueryProjection
    public CommunityDetailDto(Long id, String title, String content, int view, int likes, String createdAt, String updatedAt, String userName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.view = view;
        this.likes = likes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userName = userName;
    }

}
