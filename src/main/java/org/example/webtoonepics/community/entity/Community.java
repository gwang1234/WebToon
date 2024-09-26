package org.example.webtoonepics.community.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.webtoonepics.community.dto.CommunityWriteDto;
import org.example.webtoonepics.entity.BaseEntity.Basetime;
import org.example.webtoonepics.user.entity.User;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "community")
@AllArgsConstructor
@NoArgsConstructor
public class Community extends Basetime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private int view;
    private int likes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_info", nullable = false)
    private User user;

    public Community(Long id, String title, String content, int i, int i1, User user, LocalDateTime now,
            LocalDateTime now1) {
        super(now, now1);
        this.id = id;
        this.title = title;
        this.content = content;
        this.view = i;
        this.likes = i1;
        this.user = user;
    }

    public static Community toEntity(CommunityWriteDto writeDto, User user) {
        return new Community(
                null,
                writeDto.getTitle(),
                writeDto.getContent(),
                0,
                0,
                user,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

}
