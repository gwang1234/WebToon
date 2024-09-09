package org.example.webtoonepics.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.webtoonepics.entity.BaseEntity.Basetime;

@Entity
@Getter
public class R_comment extends Basetime {

    @Id @GeneratedValue
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
