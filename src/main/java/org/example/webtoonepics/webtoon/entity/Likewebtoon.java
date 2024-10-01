package org.example.webtoonepics.webtoon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.webtoonepics.user.entity.User;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "likewebtoon")
@IdClass(LikewebtoonId.class)
public class Likewebtoon {

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
