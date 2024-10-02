package org.example.webtoonepics.webtoon.repository;

import java.util.List;
import java.util.Optional;

import org.example.webtoonepics.main.dto.MainWebtoonDto;
import org.example.webtoonepics.user.entity.User;
import org.example.webtoonepics.webtoon.entity.Likewebtoon;
import org.example.webtoonepics.webtoon.entity.Webtoon;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LikewebtoonRepository extends JpaRepository<Likewebtoon, Long> {

    Long countByWebtoonInfoAndUserInfo(Webtoon webtoon, User user);

    Optional<Likewebtoon> findByWebtoonInfoAndUserInfo(Webtoon webtoon, User user);

    @Query("select new org.example.webtoonepics.main.dto.MainWebtoonDto(w.title, w.imageurl) " +
            "from Likewebtoon l join l.webtoonInfo w " +
            "group by w.id, w.title, w.imageurl " +
            "order by count(w.id) desc")
    List<MainWebtoonDto> findTop10(Pageable pageable);

}
