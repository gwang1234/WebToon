package org.example.webtoonepics.community.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.webtoonepics.community.entity.Community;
import org.example.webtoonepics.entity.BaseEntity.Basetime;

@Entity
@Getter
@Table(name = "c_comment")
public class C_comment extends Basetime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_comment_id")
    private Long id;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_info", nullable = false)
    private Community community;

}
