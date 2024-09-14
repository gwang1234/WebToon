package org.example.webtoonepics.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.webtoonepics.entity.BaseEntity.Basetime;

import java.io.Serializable;

@Entity
@Getter @Setter
@Table(name = "user")
public class User extends Basetime implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String userName;

    @Enumerated(EnumType.STRING)
    private Role role;

}
