package org.example.webtoonepics.community.dto;

import lombok.Data;
import org.example.webtoonepics.community.entity.Community;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class CommunityListDto {

    private Long id;
    private String title;
    private String content;
    private int view;
    private String createdAt;

    public CommunityListDto(Community community) {
        this.id = community.getId();
        this.title = community.getTitle();
        this.content = community.getContent();
        this.view = community.getView();
        this.createdAt = datetimeFormat(community.getCreated_at());
    }

    public String datetimeFormat(LocalDateTime createdAt) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM.dd");
        return createdAt.format(dateTimeFormatter);
    }

}
