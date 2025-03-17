package com.gamemetricbackend.domain.video.repository;


import com.gamemetricbackend.domain.video.dto.VideoResponseDto;
import com.gamemetricbackend.domain.video.entitiy.Video;
import com.gamemetricbackend.global.config.QuerydslConfig;
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


    @Override
    public Page<VideoResponseDto> findByTitle(String title, Pageable pageable) {
        return null;
    }

    @Override
    public Page<VideoResponseDto> getBroadcastPage(Pageable pageable) {
        return null;
    }
}
