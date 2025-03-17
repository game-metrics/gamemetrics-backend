package com.gamemetricbackend.domain.broadcast.service;

import com.gamemetricbackend.domain.broadcast.dto.BroadCastResponseDto;
import com.gamemetricbackend.domain.broadcast.dto.BroadcastCreationDto;
import com.gamemetricbackend.domain.broadcast.dto.OnOffAirRequestDto;
import com.gamemetricbackend.domain.broadcast.dto.UpdateBroadcastDto;
import com.gamemetricbackend.global.exception.UserNotMatchException;

import java.io.IOException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BroadcastService {

    Page<BroadCastResponseDto> findByTitle(String title, Pageable pageable);

    BroadCastResponseDto createBroadcast(Long userid, BroadcastCreationDto broadcastCreationDto) throws IOException;

    BroadCastResponseDto updateBroadcast(Long userId, UpdateBroadcastDto updateBroadcastDto)
        throws UserNotMatchException;

    BroadCastResponseDto OnAirBroadcast(Long userId, OnOffAirRequestDto offAirRequestDto)
            throws UserNotMatchException;

    BroadCastResponseDto OffAirBroadcast(Long userId, OnOffAirRequestDto offAirRequestDto)
        throws UserNotMatchException;

    Page<BroadCastResponseDto> getBroadcastPage(Pageable pageable);

    Boolean ConfirmBroadcast(Long broadcastId);
}
