package org.example.webtoonepics.webtoon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.webtoonepics.user.entity.User;
import org.example.webtoonepics.webtoon.dto.ReviewRequest;

@Entity
@Getter
@Table(name = "review")
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "webtoon_info", nullable = false)
    private Webtoon webtoonInfo;

    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private Double star;

    @ManyToOne
    @JoinColumn(name = "user_info", nullable = false)
    private User userInfo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Builder
    public Review(Webtoon webtoonInfo, Double star, User userInfo, String content) {
        this.webtoonInfo = webtoonInfo;
        this.star = star;
        this.userInfo = userInfo;
        this.content = content;
    }

    public void update(Double star, String content) {
        this.star = star;
        this.content = content;
    }

    public void fetch(ReviewRequest reviewRequest) {
        this.star = reviewRequest.getStar();
        this.content = reviewRequest.getContent();
    }
}
