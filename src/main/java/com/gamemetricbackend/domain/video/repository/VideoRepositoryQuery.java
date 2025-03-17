package com.gamemetricbackend.domain.video.repository;

import com.gamemetricbackend.domain.video.dto.VideoResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VideoRepositoryQuery {
    Page<VideoResponseDto> findByTitle(String title, Pageable pageable);

    Page<VideoResponseDto> getBroadcastPage(Pageable pageable);
}
