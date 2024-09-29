package org.example.webtoonepics.community.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.webtoonepics.user.entity.User;

@Entity
@Table(name = "like_community")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Like_community {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_info")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_info")
    private Community community;

    private int likes;

    public static Like_community toEntity(Community community, User user, int likes) {
        return new Like_community(null, user, community, likes+1);
    }
    public static Like_community toMinorEntity(Community community, User user, int likes) {
        return new Like_community(null, user, community, likes-1);
    }
}
