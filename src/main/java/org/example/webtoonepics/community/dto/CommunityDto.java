package org.example.webtoonepics.community.dto;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.webtoonepics.community.entity.Community;
import org.example.webtoonepics.entity.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class CommunityDto {

    private Long id;
    private String title;
    private String content;
    private int view;
    private int likes;
    private String createdAt;

    public CommunityDto(Community community) {
        this.id = community.getId();
        this.title = community.getTitle();
        this.content = community.getContent();
        this.view = community.getView();
        this.likes = community.getLikes();
        this.createdAt = datetimeFormat(community.getCreatedAt());
    }

    public String datetimeFormat(LocalDateTime createdAt) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM.dd");
        return createdAt.format(dateTimeFormatter);
    }
}
