package com.gamemetricbackend.domain.dib.repository;

import com.gamemetricbackend.domain.broadcast.entitiy.QBroadcast;
import com.gamemetricbackend.global.config.QuerydslConfig;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DibsRepositoryQueryImpl implements DibsRepositoryQuery{
    private final QuerydslConfig querydslConfig;
    QBroadcast qBroadcast = QBroadcast.broadcast;
}
