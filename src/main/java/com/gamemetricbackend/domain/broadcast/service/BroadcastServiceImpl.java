package com.gamemetricbackend.domain.broadcast.service;

import com.gamemetricbackend.domain.broadcast.dto.BroadCastResponseDto;
import com.gamemetricbackend.domain.broadcast.dto.BroadcastCreationDto;
import com.gamemetricbackend.domain.broadcast.dto.OffAirRequestDto;
import com.gamemetricbackend.domain.broadcast.dto.UpdateBroadcastDto;
import com.gamemetricbackend.domain.broadcast.entitiy.Broadcast;
import com.gamemetricbackend.domain.broadcast.repository.BroadcastRepository;
import com.gamemetricbackend.global.exception.UserNotMatchException;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BroadcastServiceImpl implements BroadcastService{
    private final BroadcastRepository broadcastRepository;

    public Optional<Broadcast> findById(Long id){
        return broadcastRepository.findById(id);
    }

    @Override
    public Page<BroadCastResponseDto> findByTitle(String title,Pageable pageable) {
        return broadcastRepository.findByTitle(title, pageable);
    }

    @Override
    public Broadcast createBroadcast(Long userid, BroadcastCreationDto broadcastCreationDto) {
        Broadcast broadcast = new Broadcast(userid,broadcastCreationDto);
        return broadcastRepository.save(broadcast);
    }

    @Override
    @Transactional
    public Broadcast updateBroadcast(Long userId, UpdateBroadcastDto updateBroadcastDto)
        throws UserNotMatchException {
        Broadcast broadcast = findById(updateBroadcastDto.getBroadCastId()).orElseThrow(() -> new NoSuchElementException("the broadcast is not findable"));
        broadcast.update(userId,updateBroadcastDto);
        return broadcast;
    }

    @Override
    @Transactional
    public Broadcast OffAirBroadcast(Long userId, OffAirRequestDto offAirRequestDto)
        throws UserNotMatchException,NoSuchElementException {
        Broadcast broadcast = findById(offAirRequestDto.getBroadcastId()).orElseThrow(()-> new NoSuchElementException("can not find the broadcast"));
        broadcast.turnOffAir(userId);
        return broadcast;
    }

    @Override
    public Page<BroadCastResponseDto> getBroadcastList(Pageable pageable) {
        return broadcastRepository.getBroadcastPage(pageable);

    }
}
