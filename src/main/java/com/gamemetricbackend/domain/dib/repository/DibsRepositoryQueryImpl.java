package com.gamemetricbackend.domain.dib.repository;

import com.gamemetricbackend.domain.dib.entity.QDib;
import com.gamemetricbackend.global.config.QuerydslConfig;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DibsRepositoryQueryImpl implements DibsRepositoryQuery{
    private final QuerydslConfig querydslConfig;
    QDib qDib = QDib.dib;
}
