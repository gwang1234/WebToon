package org.example.webtoonepics.test;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class TestDto {

    private String title;

//    @Lob
//    @Column(columnDefinition = "LONGTEXT")
    private String content;
}
