package org.example.webtoonepics.question.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.webtoonepics.entity.BaseEntity.Basetime;

@Entity
@Getter
@Table(name = "answer")
public class Answer extends Basetime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_info", nullable = false)
    private Question question;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String content;

}
