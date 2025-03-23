package com.gamemetricbackend.domain.follow.repository;

import com.gamemetricbackend.global.config.QuerydslConfig;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FollowRepositoryQueryImpl implements FollowRepositoryQuery{
    private final QuerydslConfig querydslConfig;

}
