package org.example.webtoonepics.community.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.webtoonepics.community.dto.CommunityDetailDto;
import org.example.webtoonepics.community.entity.Community;
import org.example.webtoonepics.community.entity.QCommunity;
import org.example.webtoonepics.entity.QUser;

import java.util.List;

import static org.example.webtoonepics.community.entity.QCommunity.community;
import static org.example.webtoonepics.entity.QUser.user;

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
                .select(Projections.constructor(CommunityDetailDto.class,
                        community,
                        user.userName))
                .from(community)
                .join(community.user, user)
                .where(community.id.eq(id))
                .fetch();

        return dtos.get(0);
    }
}
