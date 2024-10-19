package org.example.webtoonepics.community.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.example.webtoonepics.community.dto.CommunityDetailDto;
import org.example.webtoonepics.community.entity.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface CommunityRepository extends JpaRepository<Community, Long>, CommunityCustom {

    // 전체 검색
    Page<Community> findAllByOrderByIdDesc(Pageable pageable);

    // 제목 검색
    @Query("SELECT b FROM Community b WHERE b.title LIKE %:keyword%")
    Page<Community> findByTitleContaining(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT b FROM Community b WHERE b.title LIKE %:keyword% and b.view >= 100")
    Page<Community> findByTitleAndView100(@Param("keyword") String keyword, Pageable pageable);

    // 글내용 검색
    @Query("SELECT b FROM Community b WHERE b.content LIKE %:keyword%")
    Page<Community> findByContentContaining(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT b FROM Community b WHERE b.content LIKE %:keyword% and b.view >= 100")
    Page<Community> findByContentAndView100(@Param("keyword") String keyword, Pageable pageable);

    // 제목 + 글내용 검색
    @Query("SELECT b FROM Community b WHERE b.title LIKE %:keyword% OR b.content LIKE %:keyword%")
    Page<Community> findByTitleOrContentContaining(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT b FROM Community b WHERE (b.title LIKE %:keyword% OR b.content LIKE %:keyword%) and b.view >= 100")
    Page<Community> findByTitleOrContentAndView100(@Param("keyword") String keyword, Pageable pageable);

    // user email 가져오기
    @Query("select u.email from Community c inner join c.user u where c.id = :id")
    Optional<String> findEmail(@Param("id") Long id);

    // 조회수 업데이트
    @Modifying(clearAutomatically = true)
    @Query("update Community m set m.view = m.view + 1 where m.id = :id")
    void viewUpdate(@Param("id") Long id);


}
