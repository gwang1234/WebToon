package org.example.webtoonepics.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.webtoonepics.entity.BaseEntity.Basetime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
public class User extends Basetime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;  // 기본 키, 자동 증가

    @Column(nullable = false, length = 40, unique = true)
    private String email;  // 로그인 ID로 사용

    @Column(length = 40)
    private String password;  // 소셜 로그인 시 비워두기

    @Column(name = "user_name", nullable = false, length = 20)
    private String userName;  // 소셜 로그인 시 사용자 이름으로 설정

    @Column(nullable = false, length = 10)
    private String provider;  // 회원 가입 구분

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Builder
    public User(String email, String password, String userName, String provider, Role role) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.provider = provider != null ? provider : "local";
        this.role = role;
    }
}
