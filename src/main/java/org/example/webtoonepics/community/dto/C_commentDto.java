package org.example.webtoonepics.community.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class C_commentDto {

    private Long id;
    private String username;
    private String content;
    private String created_at;

    public C_commentDto(Long id, String username, String content, LocalDateTime created_at) {
        this.id = id;
        this.username = username;
        this.content = content;
        this.created_at = created_at.format(DateTimeFormatter.ofPattern("MM.dd HH:mm:ss"));
    }

}
