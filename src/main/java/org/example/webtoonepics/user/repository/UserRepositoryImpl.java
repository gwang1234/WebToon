package org.example.webtoonepics.user.repository;

import static org.example.webtoonepics.user.entity.QUser.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class UserRepositoryImpl implements UserRepositoryCustom {

    @PersistenceContext
    EntityManager em;
    JPAQueryFactory queryFactory;

    @PostConstruct
    public void init() {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Boolean exist(String username, String email) {

        long count = queryFactory
                .selectFrom(user)
                .where(
                        user.userName.eq(username).or(user.email.eq(email)) // and 절은 콤마로 구분 가능
                )
                .fetchCount();

        if (count == 0) {
            return false;
        }
        return true;
    }

}
