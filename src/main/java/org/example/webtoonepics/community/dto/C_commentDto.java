package org.example.webtoonepics.community.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.webtoonepics.community.entity.C_comment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class C_commentDto {

    private Long id;
    private String username;
    private String content;
    private String created_at;
    private Long community_id;
    private String title;

    public C_commentDto(Long id, String username, String content, LocalDateTime created_at, Long community_id, String title) {
        this.id = id;
        this.username = username;
        this.content = content;
        this.created_at = created_at.format(DateTimeFormatter.ofPattern("MM.dd HH:mm:ss"));
        this.community_id = community_id;
        this.title = title;
    }

    public C_commentDto(C_comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.created_at = comment.getCreated_at().format(DateTimeFormatter.ofPattern("MM.dd HH:mm:ss"));
        this.community_id = comment.getCommunity().getId();
        this.title = comment.getCommunity().getTitle();
    }

}
