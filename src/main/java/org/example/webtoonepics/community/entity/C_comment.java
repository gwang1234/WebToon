package org.example.webtoonepics.community.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.webtoonepics.community.dto.C_commentWriteDto;
import org.example.webtoonepics.entity.BaseEntity.Basetime;

import java.time.LocalDateTime;
import org.example.webtoonepics.user.entity.User;

@Entity
@Getter
@Table(name = "c_comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class C_comment extends Basetime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_comment_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_info", nullable = false)
    private Community community;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_info", nullable = false)
    private User user;

    public C_comment(Long id, User user, String content, Community community, LocalDateTime now, LocalDateTime now1) {
        super(now, now1);
        this.id = id;
        this.user = user;
        this.content = content;
        this.community = community;
    }

    public static C_comment toEntity(C_commentWriteDto writeDto, User user, Community community) {
        return new C_comment(
                writeDto.getId(),
                user,
                writeDto.getContent(),
                community,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    public void fetch(C_commentWriteDto writeDto) {
        this.content = writeDto.getContent();
        this.setUpdated_at(LocalDateTime.now());
    }
}
