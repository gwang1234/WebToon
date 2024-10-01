package org.example.webtoonepics.community.repository;

import static org.example.webtoonepics.community.entity.QCommunity.community;
import static org.example.webtoonepics.community.entity.QLike_community.like_community;
import static org.example.webtoonepics.user.entity.QUser.user;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.example.webtoonepics.community.dto.CommunityDetailDto;
import org.example.webtoonepics.community.dto.QCommunityDetailDto;

public class CommunityCustomImpl implements CommunityCustom {

    @PersistenceContext
    EntityManager em;
    JPAQueryFactory queryFactory;

    @PostConstruct
    public void init() {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public CommunityDetailDto findCommuDetail(Long id) {

        List<CommunityDetailDto> dtos = queryFactory
                .select(new QCommunityDetailDto(
                        community.id,
                        community.title,
                        community.content,
                        community.view,
                        JPAExpressions.select(like_community.likes)  // 서브쿼리로 처리
                                .from(like_community)
                                .where(like_community.id.eq(
                                        JPAExpressions.select(like_community.id.max())
                                                .from(like_community)
                                                .where(like_community.community.id.eq(id))))
                                .orderBy(like_community.id.desc()),
                        community.created_at.stringValue(),
                        community.updated_at.stringValue(),
                        user.userName))
                .from(community)
                .join(community.user, user)
                .where(community.id.eq(id))
                .fetch();

        return dtos.get(0);
    }
}
