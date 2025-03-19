package com.gamemetricbackend.domain.video.service;

import com.gamemetricbackend.domain.video.dto.VideoCreationDto;
import com.gamemetricbackend.domain.video.dto.VideoResponseDto;
import com.gamemetricbackend.domain.video.dto.VideoUpdateDto;
import com.gamemetricbackend.domain.video.entitiy.Video;
import com.gamemetricbackend.domain.video.repository.VideoRepository;
import com.gamemetricbackend.global.exception.UserNotMatchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Log4j2
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;

    @Override
    public VideoResponseDto createVideo(Long id, VideoCreationDto videoCreationDto) {

        return new VideoResponseDto(videoRepository.save(new Video(id,videoCreationDto)));
    }

    @Override
    @Transactional
    public VideoResponseDto updateVideo(Long userid, VideoUpdateDto videoUpdateDto, Long videoId) throws UserNotMatchException {
        Video video = videoRepository.findById(videoId).orElseThrow(NoSuchFieldError::new);
        video.update(userid,videoUpdateDto);
        return new VideoResponseDto(video);
    }

    @Override
    @Transactional
    public Boolean deleteVideo(Long userId, Long videoId) throws UserNotMatchException {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new NoSuchFieldError("Video not found with ID: " + videoId));

        if (!video.getUserId().equals(userId)) {
            throw new UserNotMatchException("User does not have permission");
        }

        videoRepository.delete(video);
        return true;
    }

    @Override
    public Page<VideoResponseDto> getVideoPage(Pageable pageable) {
        return videoRepository.getVideoPage(pageable);
    }

    @Override
    public VideoResponseDto getVideo(Long videoId) {
        return null;
    }
}
