package org.example.webtoonepics.question.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.webtoonepics.jwt_login.entity.BaseEntity.Basetime;
import org.example.webtoonepics.user.entity.User;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "quetion")
@NoArgsConstructor
public class Question extends Basetime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_info", nullable = false)
    private User user;

    public Question(Long id, String title, String content, User user, LocalDateTime now, LocalDateTime now1) {
        super(now, now1);
        this.id= id;
        this.title= title;
        this.content = content;
        this.user = user;
    }
}
