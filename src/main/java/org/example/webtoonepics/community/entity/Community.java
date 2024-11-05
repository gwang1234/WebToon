package org.example.webtoonepics.community.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.webtoonepics.community.dto.CommunityWriteDto;
import org.example.webtoonepics.jwt_login.entity.BaseEntity.Basetime;
import org.example.webtoonepics.user.entity.User;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "community")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Community extends Basetime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    private int view;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_info", nullable = false)
    private User user;

    public Community(Long id, String title, String content, int i, User user, LocalDateTime now, LocalDateTime now1) {
        super(now, now1);
        this.id = id;
        this.title = title;
        this.content = content;
        this.view = i;
        this.user = user;
    }

    public static Community toEntity(CommunityWriteDto writeDto, User user) {
        return new Community(
                writeDto.getId(),
                writeDto.getTitle(),
                writeDto.getContent(),
                0,
                user,
                LocalDateTime.now(),
                LocalDateTime.now()
                );
    }

    public void fetch(CommunityWriteDto updateDto) {
        if (updateDto.getContent() != null) {
            this.content = updateDto.getContent();
        }
        if (updateDto.getTitle() != null) {
            this.title = updateDto.getTitle();
        }
        this.setUpdated_at(LocalDateTime.now());
    }
}
