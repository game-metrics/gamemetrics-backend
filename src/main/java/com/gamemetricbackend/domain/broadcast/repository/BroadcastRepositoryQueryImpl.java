package com.gamemetricbackend.domain.broadcast.repository;

import com.gamemetricbackend.domain.broadcast.dto.BroadcastCreationDto;
import com.gamemetricbackend.domain.broadcast.entitiy.Broadcast;
import com.gamemetricbackend.domain.broadcast.entitiy.QBroadcast;
import com.gamemetricbackend.domain.user.entitiy.User;
import com.gamemetricbackend.global.config.QuerydslConfig;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class BroadcastRepositoryQueryImpl implements BroadcastRepositoryQuery{

    private final QuerydslConfig querydslConfig;
    QBroadcast qBroadcast = QBroadcast.broadcast;

    @Override
    public Page<Broadcast> findByTitle(String title, Pageable pageable) {

        BooleanExpression predicate = qBroadcast.title.containsIgnoreCase(title); // Use containsIgnoreCase for case-insensitive search

        List<Broadcast> broadcasts = querydslConfig.jpaQueryFactory()
            .select(Projections.fields(Broadcast.class, qBroadcast.id, qBroadcast.title, qBroadcast.broadcastStaus))
            .from(qBroadcast)
            .where(predicate)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        long total = querydslConfig.jpaQueryFactory()
            .select(qBroadcast.count())
            .from(qBroadcast)
            .where(predicate)
            .fetchOne();

        return new PageImpl<>(broadcasts, pageable, total);
    }

    @Override
    public Page<BroadcastCreationDto> getBroadcatePage(Pageable pageable) {

        List<BroadcastCreationDto> broadcasts = querydslConfig.jpaQueryFactory()
            .select(Projections.fields(BroadcastCreationDto.class, qBroadcast.id,qBroadcast.thumbNailUrl,qBroadcast.title))
            .from(qBroadcast)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long total = querydslConfig.jpaQueryFactory()
            .select(qBroadcast.count())
            .from(qBroadcast)
            .fetchOne();

        long totalCount = total != null ? total : 0L;

        return new PageImpl<>(broadcasts, pageable, totalCount);
    }
}
