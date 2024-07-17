package com.gamemetricbackend.domain.comment.repository;

import com.gamemetricbackend.domain.broadcast.entitiy.QBroadcast;
import com.gamemetricbackend.global.config.QuerydslConfig;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentRepositoryQueryQueryImpl implements CommentRepositoryQuery {

    private final QuerydslConfig querydslConfig;
    QBroadcast qBroadcast = QBroadcast.broadcast;

}
