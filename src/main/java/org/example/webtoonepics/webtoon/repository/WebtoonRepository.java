package org.example.webtoonepics.webtoon.repository;


import java.util.List;
import org.example.webtoonepics.main.dto.MainWebtoonDto;
import org.example.webtoonepics.webtoon.entity.Webtoon;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WebtoonRepository extends JpaRepository<Webtoon, Long> {

    Webtoon findByTitle(String title);


    List<Webtoon> findByProvider(String provider);

    @Query("select new org.example.webtoonepics.main.dto.MainWebtoonDto(w.id, w.title, w.imageUrl) " +
            "from Webtoon w order by w.views desc")
    List<MainWebtoonDto> findTop10Views(Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("update Webtoon w set w.views = w.views+1 where w.id = :id")
    int updateView(@Param("id")Long id);


}
