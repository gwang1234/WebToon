package org.example.webtoonepics.webtoon.repository;

import java.util.Optional;
import org.example.webtoonepics.user.entity.User;
import org.example.webtoonepics.webtoon.entity.Likewebtoon;
import org.example.webtoonepics.webtoon.entity.Webtoon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikewebtoonRepository extends JpaRepository<Likewebtoon, Long> {

    Long countByWebtoonInfo_WebtoonIdAndUserInfo_UserId(Long webtoonId, Long userId);

    Optional<Likewebtoon> findByWebtoonInfoAndUserInfo(Webtoon webtoon, User user);
}
