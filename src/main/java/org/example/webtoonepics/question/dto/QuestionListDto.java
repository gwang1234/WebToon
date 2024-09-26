package org.example.webtoonepics.question.dto;


import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class QuestionListDto {

    private Long id;
    private String title;
    private String writer;
    private String created_at;

    public QuestionListDto(Long id, String title, String writer, LocalDateTime created_at) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.created_at = DateTimeFormatter.ofPattern("MM.dd").format(created_at);
    }
}
