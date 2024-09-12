package org.example.webtoonepics.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.webtoonepics.entity.BaseEntity.Basetime;

@Entity
@Getter
@Table(name = "r_comment")
public class R_comment extends Basetime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "r_comment_id")
    private Long id;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String content;
    private short star;

    @ManyToOne
    @JoinColumn(name = "review_info", nullable = false)
    private Review review;
}
