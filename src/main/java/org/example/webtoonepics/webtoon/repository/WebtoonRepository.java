package org.example.webtoonepics.webtoon.repository;


import java.util.List;
import org.example.webtoonepics.main.dto.MainWebtoonDto;
import org.example.webtoonepics.webtoon.entity.Webtoon;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WebtoonRepository extends JpaRepository<Webtoon, Long> {

    Webtoon findByTitle(String title);


    List<Webtoon> findByProvider(String provider);

    @Query("select new org.example.webtoonepics.main.dto.MainWebtoonDto(w.title, w.imgUrl) " +
            "from Webtoon w order by w.views desc")
    List<MainWebtoonDto> findTop10Views(Pageable pageable);


}
