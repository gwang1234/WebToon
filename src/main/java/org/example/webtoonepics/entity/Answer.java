package org.example.webtoonepics.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "answer")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_info", nullable = false)
    private Question question;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String content;

}
