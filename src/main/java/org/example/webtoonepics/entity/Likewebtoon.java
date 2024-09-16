package org.example.webtoonepics.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "likewebtoon")
public class Likewebtoon {

    @Id
    @ManyToOne
    @JoinColumn(name = "webtoon_info", nullable = false)
    private Webtoon webtoonInfo;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_info", nullable = false)
    private User userInfo;

    @Column
    private int like = 0;
}
