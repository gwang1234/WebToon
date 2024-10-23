package org.example.webtoonepics.community.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.example.webtoonepics.community.dto.C_commentDto;
import org.example.webtoonepics.community.entity.C_comment;
import org.example.webtoonepics.community.entity.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<C_comment, Long> {

    @Query("select new org.example.webtoonepics.community.dto.C_commentDto(c.id, c.user.userName, c.content, c.created_at, u.id, u.title) from C_comment c inner join c.community u where u.id = :id")
    Page<C_commentDto> findWithCommunityId(Long id, Pageable pageable);

    List<C_comment> findByCommunity(Community community);

    @Query("select c from C_comment c join c.user u where u.email = :email")
    Page<C_comment> findByUser(@Param("email")String email, Pageable pageable);

}
