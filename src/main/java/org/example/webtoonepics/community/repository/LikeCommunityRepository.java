package org.example.webtoonepics.community.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.example.webtoonepics.community.entity.Community;
import org.example.webtoonepics.community.entity.Like_community;
import org.example.webtoonepics.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LikeCommunityRepository extends JpaRepository<Like_community, Long> {

    Like_community findByUserAndCommunity(User user,Community community);

    List<Like_community> findByCommunity(Community community);

    @Query("select count(l) from Like_community l where l.likes = 1 and l.community.id = :communityId")
    int likeCount(@Param("communityId")Long communityId);
}
