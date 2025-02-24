package com.gamemetricbackend.dib.serviceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.gamemetricbackend.domain.dib.entity.Dib;
import com.gamemetricbackend.domain.dib.repository.DibRepository;
import com.gamemetricbackend.domain.dib.service.DibServiceImpl;
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
    private DibRepository dibRepository;

    @InjectMocks
    private DibServiceImpl dibService;

    private final Long userId = 1L;
    private final String streamerName = "StreamerA";

    @BeforeEach
    void setUp() {
        // 초기 설정 필요하면 여기에 작성
    }

    @Test
    void updateDib_ShouldUpdateExistingDib() {
        // Given: 기존 Dib 존재
        Dib existingDib = new Dib(userId, streamerName);
        when(dibRepository.findByFollowerIdAndStreamerName(userId, streamerName))
                .thenReturn(Optional.of(existingDib));

        // When
        Boolean status = dibService.upateDib(userId, streamerName);

        // Then
        assertNotNull(status);
        verify(dibRepository, never()).save(any(Dib.class)); // 기존 객체만 업데이트
    }

    @Test
    void updateDib_ShouldCreateNewDibIfNotExist() {
        // Given: 기존 Dib 없음
        when(dibRepository.findByFollowerIdAndStreamerName(userId, streamerName))
                .thenReturn(Optional.empty());

        // When
        Boolean status = dibService.upateDib(userId, streamerName);

        // Then
        assertNotNull(status);
        verify(dibRepository).save(any(Dib.class)); // 새로운 객체 저장
    }
}
