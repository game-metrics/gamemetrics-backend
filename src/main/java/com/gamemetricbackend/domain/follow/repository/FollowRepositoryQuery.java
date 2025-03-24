package com.gamemetricbackend.domain.follow.repository;

import com.gamemetricbackend.domain.follow.dto.FollowResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FollowRepositoryQuery {

    Page<FollowResponseDto> getFollowPage(Long userId, Pageable pageable);
}
