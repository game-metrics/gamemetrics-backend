package com.gamemetricbackend.domain.user.repository;

import com.gamemetricbackend.domain.broadcast.dto.BroadCastResponseDto;
import com.gamemetricbackend.domain.user.dto.response.UserInfoResponseDto;
import com.gamemetricbackend.domain.user.entitiy.QUser;
import com.gamemetricbackend.domain.user.entitiy.User;
import com.gamemetricbackend.global.config.QuerydslConfig;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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

    @Override
    public Optional<User> findByEmail(String email) {
        BooleanExpression predicate = qUser.email.eq(email);

        return Optional.ofNullable(querydslConfig.jpaQueryFactory()
            .select(Projections.fields(User.class, qUser.id,qUser.password,qUser.nickname,qUser.role))
            .from(qUser)
            .where(predicate)
            .fetchOne());
    }

    @Override
    public Page<UserInfoResponseDto> SearchUsersByNickName(String nickName, Pageable pageable) {
        BooleanExpression predicate = qUser.nickname.eq(nickName);

        QueryResults<UserInfoResponseDto> results = querydslConfig.jpaQueryFactory()
            .select(Projections.fields(UserInfoResponseDto.class, qUser.email,qUser.nickname))
            .from(qUser)
            .where(predicate)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetchResults();

        List<UserInfoResponseDto> userList = results.getResults(); // 페이징된 결과 리스트
        Long total = results.getTotal(); // 총 개수

        long totalCount = total != null ? total : 0L;

        return new PageImpl<>(userList, pageable, totalCount);
    }
}
