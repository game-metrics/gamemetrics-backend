package com.gamemetricbackend.domain.follow.service;

import com.gamemetricbackend.domain.follow.dto.FollowResponseDto;
import org.springframework.data.domain.Page;

public interface FollowService {

    Boolean updateFollow(Long userid, String streamerName);

    Page<FollowResponseDto> getMyFollowPage(Long id);
}
