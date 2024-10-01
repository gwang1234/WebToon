package org.example.webtoonepics.webtoon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.webtoonepics.entity.BaseEntity.Basetime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "webtoon")
public class Webtoon extends Basetime {

    @Id
    @Column(name = "webtoon_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 20)
    private String provider;

    @Column
    private int views;

    @Column(nullable = false, length = 20)
    private String author;

    @Column(length = 45)
    private String genre;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "webtoonInfo")
    private List<Review> reviews;

    @Builder
    public Webtoon(String title, String provider, String author, String genre, String description) {
        this.title = title;
        this.provider = provider;
        this.author = author;
        this.genre = genre;
        this.description = description;
    }

    public void updateWith(Webtoon newWebtoon) {
        this.title = newWebtoon.getTitle();
        this.author = newWebtoon.getAuthor();
        this.genre = newWebtoon.getGenre();
        this.description = newWebtoon.getDescription();
        this.provider = newWebtoon.getProvider();
    }
}
