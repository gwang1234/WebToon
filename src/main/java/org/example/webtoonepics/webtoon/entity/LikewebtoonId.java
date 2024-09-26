package org.example.webtoonepics.webtoon.entity;

import java.io.Serializable;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class LikewebtoonId implements Serializable {

    private Long webtoonInfo;
    private Long userInfo;
}
