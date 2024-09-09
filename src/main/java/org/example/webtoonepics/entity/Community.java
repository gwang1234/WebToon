package org.example.webtoonepics.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.webtoonepics.entity.BaseEntity.Basetime;

@Entity
@Getter
public class Community extends Basetime {

    @Id @GeneratedValue
    @Column(name = "community_id")
    private Long id;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private int view;
    private int like;

    @ManyToOne
    @JoinColumn(name = "user_info", nullable = false)
    private User user;
}
