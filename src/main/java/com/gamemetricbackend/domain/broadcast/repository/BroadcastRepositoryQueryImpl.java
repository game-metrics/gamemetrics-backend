package com.gamemetricbackend.domain.broadcast.repository;

import com.gamemetricbackend.domain.broadcast.dto.BroadcastCreationDto;
import com.gamemetricbackend.domain.broadcast.entitiy.BroadcastStatus;
import com.gamemetricbackend.domain.broadcast.entitiy.QBroadcast;
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
public class BroadcastRepositoryQueryImpl implements BroadcastRepositoryQuery{

    private final QuerydslConfig querydslConfig;
    QBroadcast qBroadcast = QBroadcast.broadcast;

    @Override
    public Page<BroadcastCreationDto> findByTitle(String title, Pageable pageable) {

        BooleanExpression predicate = qBroadcast.title.containsIgnoreCase(title).and(qBroadcast.broadcastStatus.eq(BroadcastStatus.ONAIR)); // containsIgnoreCase for case-insensitive search
        System.out.println(qBroadcast.broadcastStatus);
        List<BroadcastCreationDto> results = querydslConfig.jpaQueryFactory()
            .select(Projections.fields(BroadcastCreationDto.class, qBroadcast.id,qBroadcast.thumbNailUrl, qBroadcast.title))
            .from(qBroadcast)
            .where(predicate)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        long total = results.size();

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public Page<BroadcastCreationDto> getBroadcatePage(Pageable pageable) {

        BooleanExpression predicate =qBroadcast.broadcastStatus.eq(BroadcastStatus.ONAIR); // containsIgnoreCase for case-insensitive search

        QueryResults<BroadcastCreationDto> results = querydslConfig.jpaQueryFactory()
            .select(Projections.fields(BroadcastCreationDto.class, qBroadcast.id, qBroadcast.thumbNailUrl, qBroadcast.title,qBroadcast.catagoryId))
            .from(qBroadcast)
            .where(predicate)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetchResults();

        List<BroadcastCreationDto> broadcasts = results.getResults(); // 페이징된 결과 리스트
        Long total = results.getTotal(); // 총 개수

        long totalCount = total != null ? total : 0L;

        return new PageImpl<>(broadcasts, pageable, totalCount);
    }
}
