package org.example.webtoonepics.webtoon.repository;

import java.util.List;
import java.util.Optional;

import org.example.webtoonepics.main.dto.MainWebtoonDto;
import org.example.webtoonepics.user.entity.User;
import org.example.webtoonepics.webtoon.entity.Likewebtoon;
import org.example.webtoonepics.webtoon.entity.Webtoon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

public interface LikewebtoonRepository extends JpaRepository<Likewebtoon, Long> {

    Long countByWebtoonInfoAndUserInfo(Webtoon webtoon, User user);

    Optional<Likewebtoon> findByWebtoonInfoAndUserInfo(Webtoon webtoon, User user);

    @Query("select new org.example.webtoonepics.main.dto.MainWebtoonDto(w.id, w.title, w.imageUrl) " +
            "from Likewebtoon l join l.webtoonInfo w " +
            "group by w.id, w.title, w.imageUrl " +
            "order by count(w.id) desc")
    List<MainWebtoonDto> findTop10(Pageable pageable);

    @Query("select new org.example.webtoonepics.main.dto.MainWebtoonDto(w.id, w.title, w.imageUrl) " +
            "from Likewebtoon l join l.webtoonInfo w " +
            "group by w.id, w.title, w.imageUrl " +
            "order by count(w.id) desc")
    Page<MainWebtoonDto> findTop(Pageable pageable);

    @Query("select count(l) from Likewebtoon l where l.webtoonInfo.id = :id")
    Long LikeWithWebtoon(@Param("id")Long id);

    @Query("select w from Likewebtoon l left join l.webtoonInfo w where l.userInfo.email = :useremail and l.userInfo.providerId is null")
    List<Webtoon> countByWebtoonWithJwt(@Param("useremail") String useremail);

    @Query("select w from Likewebtoon l left join  l.webtoonInfo w where l.userInfo.email = :useremail and l.userInfo.providerId is not null")
    List<Webtoon> countByWebtoonWithSocial(@Param("useremail") String useremail);
}
