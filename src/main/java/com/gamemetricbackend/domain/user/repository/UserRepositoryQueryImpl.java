package com.gamemetricbackend.domain.user.repository;

import com.gamemetricbackend.domain.user.entitiy.QUser;
import com.gamemetricbackend.domain.user.entitiy.User;
import com.gamemetricbackend.global.config.QuerydslConfig;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRepositoryQueryImpl implements UserRepositoryQuery{

    private final QuerydslConfig querydslConfig;

    QUser qUser = QUser.user;

    @Override
    public Optional<User> findPasswordById(Long id) {
        BooleanExpression predicate = qUser.id.eq(id);

        return Optional.ofNullable(querydslConfig.jpaQueryFactory()
            .select(Projections.fields(User.class, qUser.password))
            .from(qUser)
            .where(predicate)
            .fetchOne());
    }
}
