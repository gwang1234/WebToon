package org.example.webtoonepics.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class LikeWebtoon {

    @Id @GeneratedValue
    @Column(name = "like_id")
    private Long id;

    private int like;

    @ManyToOne
    @JoinColumn(name = "user_info", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "review_info", nullable = false)
    private Review review;

}
