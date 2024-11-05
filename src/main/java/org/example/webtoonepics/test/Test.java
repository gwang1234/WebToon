package org.example.webtoonepics.test;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private String title;

    @Lob
    private String content;

    public static Test toEntity(TestDto testDto) {
        Test test = new Test();
        test.setTitle(testDto.getTitle());
        test.setContent(testDto.getContent());
        return test;
    }
}
