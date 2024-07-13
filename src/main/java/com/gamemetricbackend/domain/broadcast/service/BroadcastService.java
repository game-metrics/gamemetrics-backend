package com.gamemetricbackend.domain.broadcast.service;

import com.gamemetricbackend.domain.broadcast.dto.BroadcastCreationDto;
import com.gamemetricbackend.domain.broadcast.dto.OffAirRequestDto;
import com.gamemetricbackend.domain.broadcast.dto.UpdateBroadcastDto;
import com.gamemetricbackend.domain.broadcast.entitiy.Broadcast;
import com.gamemetricbackend.global.exception.UserNotMatchException;
import java.util.Optional;

public interface BroadcastService {
    Optional<Broadcast> findById(Long id);

    Broadcast findByTitle(String title);

    Broadcast createBroadcast(Long userid, BroadcastCreationDto broadcastCreationDto);

    Broadcast updateBroadcast(Long userId, UpdateBroadcastDto updateBroadcastDto)
        throws UserNotMatchException;

    Broadcast OffAirBroadcast(Long userId, OffAirRequestDto offAirRequestDto)
        throws UserNotMatchException;
}
