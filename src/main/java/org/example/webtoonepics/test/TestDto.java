package org.example.webtoonepics.test;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class TestDto {

    private String title;
    private String content;
}
