package org.example.webtoonepics.community.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class C_commentWriteDto {

    private Long id;
    private String content;
    private String provider_id;
}
