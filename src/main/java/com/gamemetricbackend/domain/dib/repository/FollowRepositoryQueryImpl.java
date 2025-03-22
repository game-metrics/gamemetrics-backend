package com.gamemetricbackend.domain.dib.repository;

import com.gamemetricbackend.global.config.QuerydslConfig;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FollowRepositoryQueryImpl implements FollowRepositoryQuery{
    private final QuerydslConfig querydslConfig;

}
