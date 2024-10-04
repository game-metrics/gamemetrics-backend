package com.gamemetricbackend.domain.broadcast.service;

import com.gamemetricbackend.domain.broadcast.dto.BroadcastCreationDto;
import com.gamemetricbackend.domain.broadcast.dto.OffAirRequestDto;
import com.gamemetricbackend.domain.broadcast.dto.UpdateBroadcastDto;
import com.gamemetricbackend.domain.broadcast.entitiy.Broadcast;
import com.gamemetricbackend.global.exception.UserNotMatchException;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BroadcastService {
    Optional<Broadcast> findById(Long id);

    Page<Broadcast> findByTitle(String title, Pageable pageable);

    Broadcast createBroadcast(Long userid, BroadcastCreationDto broadcastCreationDto);

    Broadcast updateBroadcast(Long userId, UpdateBroadcastDto updateBroadcastDto)
        throws UserNotMatchException;

    Broadcast OffAirBroadcast(Long userId, OffAirRequestDto offAirRequestDto)
        throws UserNotMatchException;

    Page<BroadcastCreationDto> getBroadcastList(Pageable pageable);
}
