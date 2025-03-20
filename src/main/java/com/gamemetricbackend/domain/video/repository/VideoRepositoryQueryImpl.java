package com.gamemetricbackend.domain.video.repository;


import com.gamemetricbackend.domain.broadcast.dto.BroadCastResponseDto;
import com.gamemetricbackend.domain.broadcast.entitiy.BroadcastStatus;
import com.gamemetricbackend.domain.broadcast.entitiy.QBroadcast;
import com.gamemetricbackend.domain.video.dto.VideoResponseDto;
import com.gamemetricbackend.domain.video.entitiy.QVideo;
import com.gamemetricbackend.domain.video.entitiy.Video;
import com.gamemetricbackend.global.config.QuerydslConfig;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
public class VideoRepositoryQueryImpl implements VideoRepositoryQuery {
    private final QuerydslConfig querydslConfig;
    QVideo qVideo = QVideo.video;
    @Override
    public Page<VideoResponseDto> getVideoPage(Pageable pageable) {

        QueryResults<VideoResponseDto> results = querydslConfig.jpaQueryFactory()
                .select(Projections.fields(VideoResponseDto.class, qVideo.id, qVideo.title,qVideo.thumbNailUrl))
                .from(qVideo)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<VideoResponseDto> videoResponseDtos = results.getResults(); // 페이징된 결과 리스트
        Long total = results.getTotal(); // 총 개수

        long totalCount = total != null ? total : 0L;

        return new PageImpl<>(videoResponseDtos, pageable, totalCount);
    }
}
