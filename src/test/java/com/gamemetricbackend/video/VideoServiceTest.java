package com.gamemetricbackend.video;

import com.gamemetricbackend.domain.video.dto.VideoCreationDto;
import com.gamemetricbackend.domain.video.dto.VideoResponseDto;
import com.gamemetricbackend.domain.video.dto.VideoUpdateDto;
import com.gamemetricbackend.domain.video.entitiy.Video;
import com.gamemetricbackend.domain.video.repository.VideoRepository;
import com.gamemetricbackend.domain.video.service.VideoServiceImpl;
import com.gamemetricbackend.global.exception.UserNotMatchException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class VideoServiceTest {

    private VideoRepository videoRepository;
    private VideoServiceImpl videoService;

    @BeforeEach
    void setUp() {
        videoRepository = mock(VideoRepository.class);
        videoService = new VideoServiceImpl(videoRepository);
    }

    @Test
    void testCreateVideo() {
        // Arrange
        Long userId = 1L;
        VideoCreationDto dto = new VideoCreationDto("Test Title", "test_thumbnail.png","videurl");

        Video savedVideo = new Video(userId, dto);
        when(videoRepository.save(any(Video.class))).thenReturn(savedVideo);

        // Act
        VideoResponseDto response = videoService.createVideo(userId, dto);

        // Assert
        assertThat(response.getTitle()).isEqualTo("Test Title");
        assertThat(response.getThumbNailUrl()).isEqualTo("test_thumbnail.png");
    }

    @Test
    void testUpdateVideo_success() throws UserNotMatchException {
        // Arrange
        Long userId = 1L;
        Long videoId = 10L;
        VideoUpdateDto updateDto = new VideoUpdateDto("Updated Title", "updated_thumb.png");

        Video video = new Video(userId, new VideoCreationDto("Old Title", "old_thumb.png","vidurl"));
        when(videoRepository.findById(videoId)).thenReturn(Optional.of(video));

        // Act
        VideoResponseDto response = videoService.updateVideo(userId, updateDto, videoId);

        // Assert
        assertThat(response.getTitle()).isEqualTo("Updated Title");
        assertThat(response.getThumbNailUrl()).isEqualTo("updated_thumb.png");
    }

    @Test
    void testUpdateVideo_fail_wrongUser() {
        // Arrange
        Long userId = 1L;
        Long videoId = 10L;
        Long otherUserId = 2L;
        VideoUpdateDto updateDto = new VideoUpdateDto("Updated Title", "updated_thumb.png");

        Video video = new Video(otherUserId, new VideoCreationDto("Old Title", "old_thumb.png","videourl"));
        when(videoRepository.findById(videoId)).thenReturn(Optional.of(video));

        // Act & Assert
        assertThatThrownBy(() -> videoService.updateVideo(userId, updateDto, videoId))
                .isInstanceOf(UserNotMatchException.class)
                .hasMessageContaining("User does not have permission");
    }

    @Test
    void testDeleteVideo_success() throws UserNotMatchException {
        // Arrange
        Long userId = 1L;
        Long videoId = 10L;

        Video video = new Video(userId, new VideoCreationDto("Title", "thumb.png","videurl"));
        when(videoRepository.findById(videoId)).thenReturn(Optional.of(video));

        // Act
        Boolean result = videoService.deleteVideo(userId, videoId);

        // Assert
        assertThat(result).isTrue();
        verify(videoRepository, times(1)).delete(video);
    }

    @Test
    void testDeleteVideo_fail_wrongUser() {
        // Arrange
        Long userId = 1L;
        Long videoId = 10L;
        Long otherUserId = 2L;

        Video video = new Video(otherUserId, new VideoCreationDto("Title", "thumb.png","videurl"));
        when(videoRepository.findById(videoId)).thenReturn(Optional.of(video));

        // Act & Assert
        assertThatThrownBy(() -> videoService.deleteVideo(userId, videoId))
                .isInstanceOf(UserNotMatchException.class)
                .hasMessageContaining("User does not have permission");
    }
}
