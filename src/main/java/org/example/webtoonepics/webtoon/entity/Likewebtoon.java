package org.example.webtoonepics.webtoon.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.webtoonepics.user.entity.User;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "likewebtoon")
@IdClass(LikewebtoonId.class)
public class Likewebtoon {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @Id
    @ManyToOne
    @JoinColumn(name = "webtoon_info", nullable = false)
    private Webtoon webtoonInfo;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_info", nullable = false)
    private User userInfo;

    public Likewebtoon(Webtoon webtoonInfo, User userInfo) {
        this.webtoonInfo = webtoonInfo;
        this.userInfo = userInfo;
    }
}
