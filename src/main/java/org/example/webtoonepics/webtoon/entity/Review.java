package org.example.webtoonepics.webtoon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "webtoon_info", nullable = false)
    private Webtoon webtoonInfo;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private short star;

}
