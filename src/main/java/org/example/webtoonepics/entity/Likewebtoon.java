package org.example.webtoonepics.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "likewebtoon")
public class Likewebtoon {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    private int likes;

    @ManyToOne
    @JoinColumn(name = "user_info", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "review_info", nullable = false)
    private Review review;

}
