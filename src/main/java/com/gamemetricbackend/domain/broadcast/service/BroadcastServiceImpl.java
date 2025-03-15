package com.gamemetricbackend.domain.broadcast.service;

import com.gamemetricbackend.domain.broadcast.dto.BroadCastResponseDto;
import com.gamemetricbackend.domain.broadcast.dto.BroadcastCreationDto;
import com.gamemetricbackend.domain.broadcast.dto.OnOffAirRequestDto;
import com.gamemetricbackend.domain.broadcast.dto.UpdateBroadcastDto;
import com.gamemetricbackend.domain.broadcast.entitiy.Broadcast;
import com.gamemetricbackend.domain.broadcast.repository.BroadcastRepository;
import com.gamemetricbackend.global.exception.UserNotMatchException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
public class BroadcastServiceImpl implements BroadcastService{

    @Value("${hls.url}")
    private String hlsUrl;

    private final BroadcastRepository broadcastRepository;


    private Optional<Broadcast> findById(Long id){
        return broadcastRepository.findById(id);
    }

    @Override
    public Page<BroadCastResponseDto> findByTitle(String title,Pageable pageable) {
        return broadcastRepository.findByTitle(title, pageable);
    }

    @Override
    public BroadCastResponseDto createBroadcast(Long userid, BroadcastCreationDto broadcastCreationDto)  {
        return new BroadCastResponseDto(broadcastRepository.save(new Broadcast(userid,broadcastCreationDto)));
    }

    @Override
    @Transactional
    public BroadCastResponseDto updateBroadcast(Long userId, UpdateBroadcastDto updateBroadcastDto)
        throws UserNotMatchException {
        Broadcast broadcast = findById(updateBroadcastDto.getBroadCastId()).orElseThrow(() -> new NoSuchElementException("the broadcast is not findable"));
        broadcast.update(userId,updateBroadcastDto);
        return new BroadCastResponseDto(broadcast);
    }

    @Override
    @Transactional
    public BroadCastResponseDto OnAirBroadcast(Long userId, OnOffAirRequestDto onOffAirRequestDto)
            throws UserNotMatchException,NoSuchElementException {
        Broadcast broadcast = findById(onOffAirRequestDto.getBroadcastId()).orElseThrow(()-> new NoSuchElementException("can not find the broadcast"));
        broadcast.turnOnAir(userId);
        return new BroadCastResponseDto(broadcast);
    }

    @Override
    @Transactional
    public BroadCastResponseDto OffAirBroadcast(Long userId, OnOffAirRequestDto onOffAirRequestDto)
        throws UserNotMatchException,NoSuchElementException {
        Broadcast broadcast = findById(onOffAirRequestDto.getBroadcastId()).orElseThrow(()-> new NoSuchElementException("can not find the broadcast"));
        broadcast.turnOffAir(userId);
        return new BroadCastResponseDto(broadcast);
    }

    @Override
    public Page<BroadCastResponseDto> getBroadcastList(Pageable pageable) {
        return broadcastRepository.getBroadcastPage(pageable);
    }

    //
    @Override
    @Transactional
    public Boolean ConfirmBroadcast(Long broadcastId) {

        String url = hlsUrl + broadcastId + ".m3u8";

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("HEAD"); // 실제 데이터 다운로드 없이 존재 여부만 확인
            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                // HLS 스트림이 존재하므로 방송은 여전히 켜져 있음
                return false;
            } else {
                // 방송이 꺼진 상태로 간주
                log.error("방송 종료 확인 완료");
                Broadcast broadcast = findById(broadcastId).orElseThrow(()-> new NoSuchElementException("can not find the broadcast"));
                broadcast.turnOffAirForce();
                return true;
            }
        } catch (IOException e) {
            log.error("서버연결 문제");
            Broadcast broadcast = findById(broadcastId).orElseThrow(()-> new NoSuchElementException("can not find the broadcast"));
            broadcast.turnOffAirForce();

            return true;
        }
    }
}
