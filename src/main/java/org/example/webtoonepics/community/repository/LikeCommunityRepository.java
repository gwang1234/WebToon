package org.example.webtoonepics.community.repository;

import java.util.List;
import java.util.Optional;
import org.example.webtoonepics.community.entity.Community;
import org.example.webtoonepics.community.entity.Like_community;
import org.example.webtoonepics.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeCommunityRepository extends JpaRepository<Like_community, Long> {

    Boolean existsByUserAndCommunity(User user, Community community);

    Optional<Like_community> findTopByCommunityOrderByIdDesc(Community community);

    Like_community findByUserAndCommunity(User user, Community community);

    List<Like_community> findByCommunity(Community community);
}
