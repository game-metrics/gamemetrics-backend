package com.gamemetricbackend.domain.video.service;


import com.gamemetricbackend.domain.broadcast.dto.BroadCastResponseDto;
import com.gamemetricbackend.domain.video.dto.VideoCreationDto;
import com.gamemetricbackend.domain.video.dto.VideoResponseDto;
import com.gamemetricbackend.domain.video.dto.VideoUpdateDto;
import com.gamemetricbackend.global.exception.UserNotMatchException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface VideoService {


    VideoResponseDto CreateVideo(Long userId, VideoCreationDto videoCreationDto);

    VideoResponseDto UpdateVideo(Long userId, VideoUpdateDto videoUpdateDto,Long videoId) throws UserNotMatchException;

    Boolean deleteVideo(Long userId, Long videoId) throws UserNotMatchException;

    Page<VideoResponseDto> getVideoPage(Pageable pageable);
}
