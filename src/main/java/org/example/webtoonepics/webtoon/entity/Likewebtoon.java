package org.example.webtoonepics.webtoon.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.webtoonepics.user.entity.User;

@Entity
@Getter
@Table(name = "likewebtoon")
public class Likewebtoon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Id
    @ManyToOne
    @JoinColumn(name = "webtoon_info", nullable = false)
    private Webtoon webtoonInfo;

//    @Id
    @ManyToOne
    @JoinColumn(name = "user_info", nullable = false)
    private User userInfo;

    @Column
    private int likes = 0;
}
