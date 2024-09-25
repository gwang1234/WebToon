package org.example.webtoonepics.community.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.example.webtoonepics.community.dto.CommunityDetailDto;
import org.example.webtoonepics.community.entity.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CommunityRepository extends JpaRepository<Community, Long>, CommunityCustom {

    // 전체 검색
    Page<Community> findAllByOrderByIdDesc(Pageable pageable);

    // 제목 검색
    @Query("SELECT b FROM Community b WHERE b.title LIKE %:keyword%")
    Page<Community> findByTitleContaining(@Param("keyword") String keyword, Pageable pageable);

    // 글내용 검색
    @Query("SELECT b FROM Community b WHERE b.content LIKE %:keyword%")
    Page<Community> findByContentContaining(@Param("keyword") String keyword, Pageable pageable);

    // 제목 + 글내용 검색
    @Query("SELECT b FROM Community b WHERE b.title LIKE %:keyword% OR b.content LIKE %:keyword%")
    Page<Community> findByTitleOrContentContaining(@Param("keyword") String keyword, Pageable pageable);

//    @Query("select new org.example.webtoonepics.community.dto.CommunityDetailDto(c, u.userName) from Community c left join fetch c.user u where c.id = :id")
//    CommunityDetailDto findCommuDetail(@Param("id") Long id);



}