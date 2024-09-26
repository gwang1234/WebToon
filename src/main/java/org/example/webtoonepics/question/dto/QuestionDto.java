package org.example.webtoonepics.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuestionDto {

    private Long id;
    private String title;
    private String content;
}
