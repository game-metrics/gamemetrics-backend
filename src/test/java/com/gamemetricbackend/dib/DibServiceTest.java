package com.gamemetricbackend.dib;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.gamemetricbackend.domain.follow.entity.Follow;
import com.gamemetricbackend.domain.follow.repository.FollowRepository;
import com.gamemetricbackend.domain.follow.service.FollowService;
import com.gamemetricbackend.domain.follow.service.FollowServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class DibServiceImplTest {

    @Mock
    private FollowRepository followRepository;

    @InjectMocks
    private FollowServiceImpl followService;

    private final Long userId = 1L;
    private final String streamerName = "StreamerA";

    @BeforeEach
    void setUp() {
        // 초기 설정 필요하면 여기에 작성
    }

    @Test
    void updateDib_ShouldUpdateExistingDib() {
        // Given: 기존 Dib 존재
        Follow existingDib = new Follow(userId, streamerName);
        when(followRepository.findByFollowerIdAndStreamerName(userId, streamerName))
                .thenReturn(Optional.of(existingDib));

        // When
        Boolean status = followService.updateFollow(userId, streamerName);

        // Then
        assertNotNull(status);
        verify(followRepository, never()).save(any(Follow.class)); // 기존 객체만 업데이트
    }

    @Test
    void updateDib_ShouldCreateNewDibIfNotExist() {
        // Given: 기존 Dib 없음
        when(followRepository.findByFollowerIdAndStreamerName(userId, streamerName))
                .thenReturn(Optional.empty());

        // When
        Boolean status = followService.updateFollow(userId, streamerName);

        // Then
        assertNotNull(status);
        verify(followRepository).save(any(Follow.class)); // 새로운 객체 저장
    }
}
