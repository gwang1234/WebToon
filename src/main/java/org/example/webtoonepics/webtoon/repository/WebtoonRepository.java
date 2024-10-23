package org.example.webtoonepics.webtoon.repository;


import java.util.List;

import org.example.webtoonepics.community.entity.Community;
import org.example.webtoonepics.main.dto.MainWebtoonDto;
import org.example.webtoonepics.webtoon.entity.Webtoon;
import org.springframework.data.domain.Page;
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

    @Query("select new org.example.webtoonepics.main.dto.MainWebtoonDto(w.id, w.title, w.imageUrl) " +
            "from Webtoon w order by w.views desc")
    Page<MainWebtoonDto> findViews(Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("update Webtoon w set w.views = w.views+1 where w.id = :id")
    int updateView(@Param("id")Long id);

    @Query("SELECT b FROM Webtoon b WHERE b.title LIKE %:keyword%")
    Page<Webtoon> findByTitleContaining(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT b FROM Webtoon b WHERE b.description LIKE %:keyword%")
    Page<Webtoon> findByDescriptionContaining(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT b FROM Webtoon b WHERE b.author LIKE %:keyword%")
    Page<Webtoon> findByAuthorContaining(@Param("keyword") String keyword, Pageable pageable);

    Page<Webtoon> findByGenre(@Param("genre") String genre, Pageable pageable);
}
