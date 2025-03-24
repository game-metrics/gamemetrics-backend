package com.gamemetricbackend.domain.follow.repository;

import com.gamemetricbackend.domain.follow.dto.FollowResponseDto;
import com.gamemetricbackend.domain.follow.entity.QFollow;
import com.gamemetricbackend.domain.video.dto.VideoResponseDto;
import com.gamemetricbackend.domain.video.entitiy.QVideo;
import com.gamemetricbackend.global.config.QuerydslConfig;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class FollowRepositoryQueryImpl implements FollowRepositoryQuery{
    private final QuerydslConfig querydslConfig;
    QFollow qFollow = QFollow.follow;

    @Override
    public Page<FollowResponseDto> getFollowPage(Long userId, Pageable pageable) {

        BooleanExpression predicate = qFollow.followerId.eq(userId);

        QueryResults<FollowResponseDto> results = querydslConfig.jpaQueryFactory()
            .select(Projections.fields(FollowResponseDto.class, qFollow.streamerName))
            .from(qFollow)
            .where(predicate)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetchResults();

        List<FollowResponseDto> followResponseDtos = results.getResults(); // 페이징된 결과 리스트
        Long total = results.getTotal(); // 총 개수

        long totalCount = total != null ? total : 0L;

        return new PageImpl<>(followResponseDtos, pageable, totalCount);
    }
}
